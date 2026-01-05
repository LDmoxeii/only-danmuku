# 视频投稿处理聚合拆分与流程编排迭代设计

## 背景与目标
现有“扁平聚合”与“三层聚合”方案分别带来流程碎片化与聚合过大问题，且与规约约束存在冲突。目标：
- 请求只触发一个核心命令（CreateVideoPost）；后续处理由事件与调度编排驱动。
- 处理状态集中到独立聚合，避免跨聚合状态同步风暴。
- 命令仅加载/保存聚合；查询与防腐层调用由控制器/监听器编排完成。
- 防腐层仅处理路径/对象前缀等外部信息，不接触数据库 ID。

## 设计范围
- 新增 video_post_processing 聚合（处理聚合）及文件子实体。
- 新增事件监听器编排与回写命令。
- video_file_post 作为 video_post 子实体，用于未审核稿件的文件信息查询，处理完成后回填结果。
- video_file_post 字段优化：video_id 更名为 video_post_id，新增产物摘要字段，移除处理阶段冗余字段。
- 处理侧新增分辨率子实体 `video_post_processing_variant`；稿件侧新增分辨率子实体 `video_file_post_variant`。
- 分辨率策略聚合独立设置，处理流程中不保存授权策略。
- 复用 VideoPost、Video、VideoFileUploadSession 等既有聚合，并在 Video 下补充 VideoFileVariant 子实体。
- 本次迭代不包含“获取处理进度”功能与其读模型表。

## 聚合划分与职责
- VideoPost（root）：投稿业务元信息与审核状态；仅响应处理聚合事件推进整体状态。
- VideoPostProcessing（root）：维护稿件处理状态、文件清单、成功/失败计数；只有聚合根发布处理事件。
- VideoPostProcessingFile（child）：记录 fileIndex 维度的处理状态与输出前缀；不暴露内部 ID。
- VideoPostProcessingVariant（child）：记录分辨率档位与 m3u8 产物。
- VideoFilePost（child，归属 VideoPost）：稿件未审核阶段的文件查询实体；由处理完成事件回填转码/加密产物摘要。
- VideoFilePostVariant（child）：记录稿件侧分辨率档位，处理完成后一次性回填。
- VideoQualityPolicy（root）：分辨率策略聚合，处理完成后再配置策略。
- VideoHlsEncryptKey/VideoHlsKeyToken（root）：处理期以 `videoPostId + fileIndex` 关联，审核通过后补充 `videoId`。

## 关联键策略（方案A）
- 处理期：`VideoHlsEncryptKey`/`VideoHlsKeyToken` 仅绑定 `videoPostId + fileIndex`。
- 转正后：`VideoAuditPassedDomainEvent` 触发补充 `videoId`，播放侧按 `videoId + fileIndex` 使用。
- 策略聚合：`VideoQualityPolicy` 仅使用 `videoId + fileIndex + quality`，在转正后配置。
- 约束：不使用文件路径作为业务关联键，路径仅作为 ACL 调用参数。

## 实体关系确认与标记
- 关系确认：video_post 为聚合根，video_file_post 为其子实体；video_post_processing 为聚合根，video_post_processing_file 为子实体。
- 实现要求：
  - VideoPost 使用 `@Aggregate(root = true)`（或项目等效标记）明确聚合根角色。
  - video_file_post 表注释标记 `@P=video_post`，VideoFilePost 使用 `@Aggregate(root = false)`。
  - VideoPostProcessing 使用 `@Aggregate(root = true)`（或项目等效标记）明确聚合根角色。
  - video_post_processing 表注释使用 `@Spe;@Fac;` 标记聚合根能力（与现有约定一致）。
  - VideoPostProcessingFile 使用 `@Aggregate(root = false)`，并在表注释中标记 `@P=video_post_processing`（项目既有约定）。
  - VideoPostProcessingVariant 同样标记 `@Aggregate(root = false)` 且表注释使用 `@P=video_post_processing_file`。
  - VideoFilePostVariant 标记 `@Aggregate(root = false)` 且表注释使用 `@P=video_file_post`。

## 编排与事件
1) 请求层：Controller -> CreateVideoPostCmd（单命令）。
2) 事件触发：VideoPostCreatedDomainEvent -> StartVideoPostProcessingCmd。
3) VideoPostProcessingStartedDomainEvent -> 监听器转码编排 -> ApplyVideoPostProcessingTranscodeResultCmd。
4) VideoPostProcessingTranscodeCompletedDomainEvent -> 监听器加密编排 -> ApplyVideoPostProcessingEncryptResultCmd。
5) VideoPostProcessingCompletedDomainEvent -> SyncVideoPostProcessStatusCmd + SyncVideoFilePostFromProcessingCmd 回填 VideoFilePost。
6) VideoAuditPassedDomainEvent -> 绑定加密信息至 videoId + 设置策略（可选）。

说明：命令不包含查询或 ACL 调用；ACL 入参仅为 path/objectPrefix 等外部信息。

## 流程设计优化与决策

### 采用事件驱动（基于 Spring 事件 + 发信箱）
- 决策：采用事件触发的监听器编排，不引入轮询 Job。
- 理由：现有发信箱已具备可靠投递与重试能力，引入 Job 将增加基础设施与运维成本。
- 边界：监听器可编排查询+ACL+命令，命令本身不调用 ACL，保持规约一致。

## 事件监听器查询目标与数据源（精确定义）

### 转码监听器（VideoPostProcessingStartedDomainEvent）
- 事件输入：videoPostId + fileList(uploadId, fileIndex, outputDir, objectPrefix, encOutputDir)。
- 查询目标：`video_file_upload_session`（通过 uploadId 获取 tempPath）。
- 前置条件：uploadSession.status = DONE 且 tempPath 非空。
- 处理：MergeUploadToMp4ByPathCli -> TranscodeVideoFileToAbrByPathCli -> UploadVideoAbrOutputCli -> ApplyVideoPostProcessingTranscodeResultCmd。
- 说明：StartVideoPostProcessingCmd 负责为每个文件预生成 outputDir/objectPrefix/encOutputDir 并落库与入事件。

### 加密监听器（VideoPostProcessingTranscodeCompletedDomainEvent）
- 事件输入：videoPostId + fileIndex + transcodeOutputPrefix + encOutputDir + variantsJson。
- 前置条件：transcode_status = SUCCESS 且 variantsJson 非空。
- 处理：GenerateVideoPostQualityKeysCmd -> EncryptHlsWithQualityKeysCli -> ApplyVideoPostProcessingEncryptResultCmd。

### 完成监听器（VideoPostProcessingCompletedDomainEvent）
- 事件输入：videoPostId + duration/failedCount 等汇总信息。
- 查询目标：`video_post_processing_file`（主表）+ `video_post_processing_variant`。
- 前置条件：处理聚合内 `isAllStepsCompleted()` 成立。
- 处理：ListVideoPostProcessingFilesForSync -> SyncVideoFilePostFromProcessingCmd（加载 VideoPost 聚合，按 fileIndex 回填子实体与分辨率档位）。

## 命令处理与事件触发（精确定义）

### ApplyVideoPostProcessingTranscodeResultCmd
- 职责：仅加载 `VideoPostProcessing` 聚合并更新单个 `fileIndex` 的转码状态与产物信息。
- 完成事件触发逻辑（伪代码）：
```text
handle(cmd):
  proc = repo.loadByVideoPostId(cmd.videoPostId)
  proc.applyTranscodeResult(cmd.fileIndex, cmd.success, cmd.outputPrefix, cmd.outputPath,
                            cmd.duration, cmd.fileSize, cmd.variantsJson, cmd.failReason)

  if cmd.success:
      proc.raise(VideoPostProcessingTranscodeCompletedDomainEvent(cmd.videoPostId,
                                                                  cmd.fileIndex,
                                                                  cmd.outputPrefix,
                                                                  proc.getEncOutputDir(cmd.fileIndex),
                                                                  cmd.variantsJson))

  if proc.isAllStepsCompleted():
      proc.raise(VideoPostProcessingCompletedDomainEvent(proc.videoPostId,
                                                         proc.totalDuration,
                                                         proc.failedCount,
                                                         proc.lastFailReason))
  save(proc)

VideoPostProcessing.isAllStepsCompleted():
  return all files:
    transcode_status == SUCCESS
    AND (encrypt_status == SUCCESS OR encrypt_status == SKIPPED)
    AND failed_count == 0
```
- 说明：
  - 转码成功时发布 `VideoPostProcessingTranscodeCompletedDomainEvent`，用于驱动加密编排。
  - `isAllStepsCompleted` 在聚合内完成判断，不需要查询外部表。
  - 加密不要求的场景通过 `SKIPPED` 直接满足完成条件。

### ApplyVideoPostProcessingEncryptResultCmd
- 逻辑同上：更新加密结果后调用 `isAllStepsCompleted()`，若满足则发布同一个完成事件。

## 事件监听与状态同步
- `VideoPostProcessingCompletedDomainEvent` 由处理聚合根发布。
- Subscriber 行为：
  - 接收事件 -> 发送 `SyncVideoPostProcessStatusCmd(videoPostId, targetStatus, duration, failReason)`。
  - `SyncVideoPostProcessStatusCmd` 仅加载 `VideoPost` 聚合并更新状态，如 `PENDING_REVIEW`。
  - 同步 VideoFilePost：先查询 `ListVideoPostProcessingFilesForSync(videoPostId)`，再逐文件发送 `SyncVideoFilePostFromProcessingCmd(videoPostId, fileIndex, ...)`，命令加载 VideoPost 聚合更新子实体。
  - 审核通过后：`VideoAuditPassedDomainEvent` 触发查询密钥/Token 列表，再逐条发送 `BindVideoHlsEncryptKeyToVideoCmd`/`BindVideoHlsKeyTokenToVideoCmd` 补充 `videoId`（策略可由后台独立设置）。
  - 若 `failedCount > 0`，可扩展 `VideoPostProcessingFailedDomainEvent` 走失败链路（非本轮必需）。

## 端到端流程（步骤 + 伪代码）
1) Controller -> `CreateVideoPostCmd` 创建稿件，`VideoPost` 状态设为 `TRANSCODING`。
2) `VideoPostCreatedDomainEvent` 触发 `StartVideoPostProcessingCmd`，初始化处理聚合与文件清单。
3) 转码监听器：
```text
on VideoPostProcessingStartedDomainEvent:
  for file in event.fileList:
    tempPath = GetUploadSessionTempPathQry(file.uploadId)
    merged = MergeUploadToMp4ByPathCli(tempPath, file.outputDir)
    variants = TranscodeVideoFileToAbrByPathCli(merged.mergedMp4Path, file.outputDir)
    storage = UploadVideoAbrOutputCli(file.outputDir, file.objectPrefix)
    ApplyVideoPostProcessingTranscodeResultCmd(...)
```
4) 加密监听器：
```text
on VideoPostProcessingTranscodeCompletedDomainEvent:
  keys = GenerateVideoPostQualityKeysCmd(event.videoPostId, event.fileIndex, qualities(event.variantsJson))
  enc = EncryptHlsWithQualityKeysCli(event.transcodeOutputPrefix, event.encOutputDir, keys.keysJson)
  ApplyVideoPostProcessingEncryptResultCmd(...)
```
5) 聚合内 `isAllStepsCompleted()` 成立时发布 `VideoPostProcessingCompletedDomainEvent`。
6) Subscriber -> `SyncVideoPostProcessStatusCmd` 更新 `VideoPost` 至 `PENDING_REVIEW`。
7) 同步 VideoFilePost：
```text
on VideoPostProcessingCompletedDomainEvent:
  files = ListVideoPostProcessingFilesForSync(videoPostId)
  for file in files:
    SyncVideoFilePostFromProcessingCmd(videoPostId, file.fileIndex,
                                       file.transcodeOutputPrefix,
                                       file.encryptOutputPrefix,
                                       file.variantsJson,
                                       file.duration,
                                       file.fileSize,
                                       file.encryptMethod,
                                       file.keyVersion)
```
8) 审核通过后（`VideoAuditPassedDomainEvent`）：
```text
on VideoAuditPassedDomainEvent:
  keys = ListVideoHlsEncryptKeysByPostFile(videoPostId, fileIndex)
  tokens = ListVideoHlsKeyTokensByPostFile(videoPostId, fileIndex)
  for key in keys:
    BindVideoHlsEncryptKeyToVideoCmd(key.id, videoId)
  for token in tokens:
    BindVideoHlsKeyTokenToVideoCmd(token.id, videoId)
  // VideoQualityPolicy 由后台在转正后配置
```

## 本次迭代排除项
- 不实现“获取处理进度”相关 Query/读模型与 API。

## 交付物
- 需求/设计：iterate/video-post-processing-workflow/video-post-processing-workflow.md
- 迁移方案：iterate/video-post-processing-workflow/video-post-processing-workflow-video-file-post-optimize.md
- DDD 设计 JSON：iterate/video-post-processing-workflow/video_post_processing_workflow_gen.json
- SQL 变更：iterate/video-post-processing-workflow/video_post_processing_workflow_update.sql
- video_file_post 优化 SQL：iterate/video-post-processing-workflow/video_post_processing_video_file_post_optimize.sql
- 聚合图：iterate/video-post-processing-workflow/video-domain-aggregates-processing-workflow.mmd
- 流程图：iterate/video-post-processing-workflow/video-post-processing-workflow-flow.mmd
