# 视频稿件创建流程

## 流程描述
- 前端调用 `/ucenter/postVideo` 且 `videoId` 为空，进入新稿件创建分支。
- `CreateVideoPostCmd` 通过 `Mediator.factories.create` 创建 `VideoPost`，自动触发 `onCreate` 并发布 `VideoDraftCreatedDomainEvent`，随后保存并返回 `videoId`。
- 对 `uploadFileList` 中每个文件，调用 `CreateVideoFilePostCmd` 创建 `VideoFilePost`；`Mediator.factories.create` 触发 `onCreate` 并发布 `VideoFilePostCreatedDomainEvent`。
- `VideoFilePostCreatedDomainEventSubscriber` 处理转码链路：获取临时目录、合并分片、探测参数、执行 ABR 转码、上传输出，并回写转码结果（成功/失败）。
- 回写结果触发 `VideoFilePostTranscodeResultUpdatedDomainEvent`：刷新稿件转码状态；若转码成功且未加密，则继续加密流程。
- 加密流程中 `GenerateVideoHlsQualityKeysCmd` 生成各清晰度密钥（`Mediator.factories.create` 触发 `VideoHlsEncryptKey.onCreate`），随后加密输出并持久化加密结果；`VideoHlsEncryptKeyCreatedDomainEventSubscriber` 同步清晰度授权策略。

## 时序图
```mermaidsequenceDiagram
  autonumber
  actor FE as Frontend
  participant Ctrl as CompatibleUCenterVideoPostController
  participant CreatePost as CreateVideoPostCmd
  participant VideoPost as VideoPost
  participant CreateFile as CreateVideoFilePostCmd
  participant FilePost as VideoFilePost
  participant FileCreatedSub as VideoFilePostCreatedDomainEventSubscriber
  participant TempQry as GetUploadSessionTempPathQry
  participant MergeCli as MergeUploadToMp4Cli
  participant Probe as FFprobeUtils
  participant TranscodeCli as TranscodeVideoFileToAbrCli
  participant UploadCli as UploadVideoAbrOutputCli
  participant UpdateTranscode as UpdateVideoFilePostTranscodeResultCmd
  participant FileUpdatedSub as VideoFilePostTranscodeResultUpdatedDomainEventSubscriber
  participant RefreshStatus as RefreshVideoPostTranscodeStatusCmd
  participant GenKeys as GenerateVideoHlsQualityKeysCmd
  participant Key as VideoHlsEncryptKey
  participant KeyCreatedSub as VideoHlsEncryptKeyCreatedDomainEventSubscriber
  participant UpsertAuth as UpsertVideoHlsQualityAuthCmd
  participant EncryptCli as EncryptHlsWithQualityKeysCli
  participant PersistEncrypt as PersistVideoEncryptBatchResultCmd

  FE->>Ctrl: POST /ucenter/postVideo (videoId=null, uploadFileList)
  Ctrl->>CreatePost: CreateVideoPostCmd.Request(...)
  CreatePost->>VideoPost: Mediator.factories.create(VideoPostFactory.Payload)
  Note over VideoPost: onCreate auto-triggered by Mediator.factories.create
  VideoPost-->>CreatePost: VideoDraftCreatedDomainEvent
  CreatePost->>CreatePost: UoW save
  CreatePost-->>Ctrl: videoId
  Ctrl-->>FE: response

  loop each upload file item
    Ctrl->>CreateFile: CreateVideoFilePostCmd.Request(uploadId, videoId, fileIndex, fileName)
    CreateFile->>FilePost: Mediator.factories.create(VideoFilePostFactory.Payload)
    Note over FilePost: onCreate auto-triggered by Mediator.factories.create
    FilePost-->>CreateFile: VideoFilePostCreatedDomainEvent
    CreateFile->>CreateFile: UoW save
    FilePost-->>FileCreatedSub: VideoFilePostCreatedDomainEvent

    FileCreatedSub->>TempQry: GetUploadSessionTempPathQry(uploadId)
    TempQry-->>FileCreatedSub: tempPath
    FileCreatedSub->>MergeCli: MergeUploadToMp4Cli(tempPath)
    MergeCli-->>FileCreatedSub: mergedMp4Path, outputDir, duration, fileSize
    FileCreatedSub->>Probe: probeVideoResolution(mergedMp4Path)
    Probe-->>FileCreatedSub: width, height, bitrateKbps
    FileCreatedSub->>TranscodeCli: TranscodeVideoFileToAbrCli(sourcePath, profiles)
    TranscodeCli-->>FileCreatedSub: accepted, variants
    FileCreatedSub->>UploadCli: UploadVideoAbrOutputCli(outputDir)
    UploadCli-->>FileCreatedSub: success, storagePrefix
    alt transcode/upload success
      FileCreatedSub->>UpdateTranscode: UpdateVideoFilePostTranscodeResultCmd(success=true,...)
    else failure
      FileCreatedSub->>UpdateTranscode: UpdateVideoFilePostTranscodeResultCmd(success=false,...)
    end

    UpdateTranscode->>FilePost: applyTranscodeResult(...)
    FilePost-->>FileUpdatedSub: VideoFilePostTranscodeResultUpdatedDomainEvent
    FileUpdatedSub->>RefreshStatus: RefreshVideoPostTranscodeStatusCmd(videoPostId)

    alt success and encryptStatus=UNENCRYPTED
      FileUpdatedSub->>GenKeys: GenerateVideoHlsQualityKeysCmd(qualities, method)
      GenKeys->>Key: Mediator.factories.create(VideoHlsEncryptKeyFactory.Payload) xN
      Note over Key: onCreate auto-triggered by Mediator.factories.create
      Key-->>KeyCreatedSub: VideoHlsEncryptKeyCreatedDomainEvent
      KeyCreatedSub->>UpsertAuth: UpsertVideoHlsQualityAuthCmd
      GenKeys-->>FileUpdatedSub: keysJson, keyVersion
      FileUpdatedSub->>EncryptCli: EncryptHlsWithQualityKeysCli(sourceDir, outputDir, keysJson)
      EncryptCli-->>FileUpdatedSub: success, encrypted paths
      FileUpdatedSub->>PersistEncrypt: PersistVideoEncryptBatchResultCmd(...)
    end
  end
```
