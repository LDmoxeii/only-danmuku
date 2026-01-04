# 视频文件预上传流程

## 流程描述
- 前端调用 `/file/preUploadVideo`，创建上传会话。
- `CreateUploadSessionCmd` 通过 `Mediator.factories.create` 创建 `VideoFileUploadSession`，自动触发 `onCreate` 并发布 `UploadSessionCreatedDomainEvent`。
- 事件订阅者创建临时目录（`CreateUploadSessionTempDirCli`），随后执行 `InitTempAndStartUploadingCmd` 设置 `tempPath` 并标记为上传中。
- 每个分片上传时，前端调用 `/file/uploadVideo`。
- 控制器查询会话 `tempPath`，将分片写入临时存储（`UploadVideoChunkStorageCli`）。
- `UploadVideoChunkCmd` 校验归属/状态/分片索引，并更新会话状态、分片进度与已上传大小；若为最后分片则标记完成。

## 时序图
```mermaidsequenceDiagram
  autonumber
  actor FE as Frontend
  participant C as CompatibleFileController
  participant Cmd as CreateUploadSessionCmd
  participant Sess as VideoFileUploadSession
  participant Sub as UploadSessionCreatedDomainEventSubscriber
  participant TempCli as CreateUploadSessionTempDirCli
  participant InitCmd as InitTempAndStartUploadingCmd
  participant Qry as GetUploadSessionTempPathQry
  participant Storage as UploadVideoChunkStorageCli
  participant UCmd as UploadVideoChunkCmd

  FE->>C: POST /file/preUploadVideo(fileName, chunks)
  C->>Cmd: CreateUploadSessionCmd.Request(customerId, fileName, chunks)
  Cmd->>Sess: create via factory (status=CREATED, expiresAt=now+24h)
  Cmd->>Cmd: UoW save
  Sess-->>Sub: UploadSessionCreatedDomainEvent
  Sub->>TempCli: CreateUploadSessionTempDirCli.Request(uploadId)
  TempCli-->>Sub: tempPath
  Sub->>InitCmd: InitTempAndStartUploadingCmd.Request(uploadId, tempPath)
  InitCmd->>Sess: initTempAndStartUploading (status=UPLOADING, tempPath set)
  InitCmd->>InitCmd: UoW save
  Cmd-->>C: uploadId
  C-->>FE: uploadId

  loop each chunk
    FE->>C: POST /file/uploadVideo(chunkFile, chunkIndex, uploadId)
    C->>Qry: GetUploadSessionTempPathQry(uploadId)
    Qry-->>C: tempPath
    C->>Storage: UploadVideoChunkStorageCli(tempPath, chunkIndex, chunkFile)
    Storage-->>C: storedPath, size
    C->>UCmd: UploadVideoChunkCmd(customerId, uploadId, chunkIndex, size)
    UCmd->>Sess: ensureOwnedBy / ensureActive / ensureChunkAllowed
    UCmd->>Sess: onChunkUploaded (status=UPLOADING, update index/size)
    alt last chunk
      UCmd->>Sess: tryMarkDoneIfComplete (status=DONE)
    end
    UCmd->>UCmd: UoW save
    C-->>FE: success
  end
```
