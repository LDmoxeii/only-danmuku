# 视频稿件转码/加密 Job 编排流程

## 流程描述
- Job A（转码编排）先通过 Qry 扫描 `transfer_result=TRANSCODING` 的分P，返回 `videoPostId + fileIndex + uploadId`。
- Scheduler 外部通过 Qry 查询 `tempPath`，执行合并/转码/上传 CLI，并把结果通过 `ApplyVideoPostTranscodeResultCmd` 回写到聚合。
- Job B（加密编排）先通过 Qry 扫描 `transfer_result=SUCCESS && encrypt_status=UNENCRYPTED` 的分P，返回 `videoPostId + fileIndex`。
- Scheduler 外部通过 Qry 获取 `qualities` 与 `filePath`；先调用 `GenerateVideoPostQualityKeysCmd` 生成 key，再执行加密 CLI，最终用 `ApplyVideoPostEncryptResultCmd` 回写到聚合。
- Cmd 内仅做聚合变更，不调用 Qry/CLI；失败场景可重试，幂等性由输出目录与 keyVersion 控制。

## 时序图
```mermaidsequenceDiagram
  autonumber
  participant Scheduler as Scheduler/Job
  participant Qry as Job Query
  participant Cmd as Job Command
  participant Repo as Repository
  participant Upload as UploadSession Repo
  participant Transcode as Transcode CLI
  participant Storage as OSS Storage CLI
  participant Encrypt as Encrypt CLI

  Note over Scheduler,Cmd: Job A - 转码编排
  Scheduler->>Qry: ListTranscodingVideoPosts(batchSize)
  Qry-->>Scheduler: videoPostId + fileIndex
  loop each item
    Scheduler->>Cmd: ProcessVideoPostTranscode(videoPostId, fileIndex)
    Cmd->>Repo: load VideoPost aggregate
    Cmd->>Upload: load UploadSession aggregate
    Cmd->>Transcode: MergeUploadToMp4Cli(filePost)
    Transcode-->>Cmd: mergedMp4Path + outputDir + duration + fileSize
    Cmd->>Transcode: TranscodeVideoFileToAbrCli(mergedMp4Path)
    Transcode-->>Cmd: abrVariants
    Cmd->>Storage: UploadVideoAbrOutputCli(outputDir)
    Storage-->>Cmd: storagePrefix
    Cmd->>Repo: save VideoPost aggregate
  end

  Note over Scheduler,Cmd: Job B - 加密编排
  Scheduler->>Qry: ListEncryptPendingVideoPosts(batchSize)
  Qry-->>Scheduler: videoPostId + fileIndex
  loop each item
    Scheduler->>Cmd: ProcessVideoPostEncrypt(videoPostId, fileIndex)
    Cmd->>Repo: load VideoPost aggregate
    Cmd->>Encrypt: EncryptHlsWithQualityKeysCli(filePath, keysJson)
    Encrypt-->>Cmd: encryptedMasterPath + encryptedVariants
    Cmd->>Repo: save VideoPost aggregate
  end
```
