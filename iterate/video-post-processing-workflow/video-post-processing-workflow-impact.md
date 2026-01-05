# 视频稿件处理聚合调整影响面清单

## 背景范围
- 处理侧：`video_post_processing` -> `video_post_processing_file` -> `video_post_processing_variant`。
- 稿件侧：`video_post` -> `video_file_post` -> `video_file_post_variant`（仅保留处理完成态）。
- 视频侧：`video` -> `video_file` -> `video_file_variant`（播放侧分辨率档位）。
- `video_post_processing` 去掉 `customer_id`。
- 分辨率策略聚合独立设置，流程中不保存授权策略。
- `video_hls_encrypt_key`/`video_hls_key_token` 处理期使用 `video_post_id + file_index`，转正后补 `video_id`。

## 接口影响
### 需要更新
- 兼容投稿接口改为单命令触发，不再直接创建/删除 `video_file_post`。`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/portal/api/compatible/CompatibleUCenterVideoPostController.kt`
- ABR 管理接口读取路径与档位数据需调整为新表或回填字段。`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/portal/api/admin/VideoAbrAdminController.kt`

### 后续无用/建议下线
- 直接依赖 `video_file_post` 聚合命令的旧流程接口分支（Create/Delete/Refresh）应移除。`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/portal/api/compatible/CompatibleUCenterVideoPostController.kt`

## 查询影响
### 需要更新
- `GetVideoFilePostsByPostIdQry`：字段与状态源改为回填后展示（不再读取 ABR 子表）。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/video_file_post/GetVideoFilePostsByPostIdQry.kt`
- `GetVideoPostInfoQry`：移除 `update_type`/`transfer_result` 相关依赖或映射到新摘要字段。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/video_draft/GetVideoPostInfoQry.kt`
- `GetVideoFilePostPathQry`：基础路径来源调整为回填字段。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/video_transcode/GetVideoFilePostPathQry.kt`
- `GetVideoAbrMasterQry`：读取转码状态/路径的来源需切换为新表或回填字段。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/video_transcode/GetVideoAbrMasterQry.kt`
- `ListVideoAbrVariantsQry`：档位列表切换到 `video_file_post_variant`（处理完成态）。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/video_transcode/ListVideoAbrVariantsQry.kt`
- `GetVideoEncryptStatusQry`：加密状态与密钥字段变化需调整。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/video_encrypt/GetVideoEncryptStatusQry.kt`
- 新增绑定前置查询：按 `videoPostId + fileIndex` 列出密钥与 token ID，用于转正绑定。`only-danmuku/iterate/video-post-processing-workflow/video_post_processing_workflow_gen.json`
- 查询模型字段同步：`video_file_post` 新字段与删除字段要反映到查询 DTO。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/_share/model/VideoFilePost.kt`
- 查询 Handler 同步字段与来源：`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/application/queries/video_transcode/GetVideoFilePostPathQryHandler.kt`
- 查询 Handler 同步字段与来源：`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/application/queries/video_transcode/GetVideoAbrMasterQryHandler.kt`
- 查询 Handler 同步字段与来源：`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/application/queries/video_file_post/GetVideoFilePostsByPostIdQryHandler.kt`
- 查询 Handler 同步字段与来源：`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/application/queries/video_encrypt/GetVideoEncryptStatusQryHandler.kt`
- 查询 Handler 同步字段与来源：`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/application/queries/video_draft/GetVideoPostInfoQryHandler.kt`
- 查询 Handler 同步字段与来源：`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/application/queries/video/GetVideoPlayFilesQryHandler.kt`
- 播放侧清晰度列表/路径查询改读 `video_file_variant`。`only-danmuku/design/sql/summary.sql`

### 后续无用/建议下线
- `UniqueVideoHlsAbrVariantQualityQry`：旧 ABR 子表移除后不再适用。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/video_file_post/UniqueVideoHlsAbrVariantQualityQry.kt`
- 对应 Handler 需下线。`only-danmuku/only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/application/queries/video_file_post/UniqueVideoHlsAbrVariantQualityQryHandler.kt`
- `video_hls_quality_auth` 相关查询与模型不再使用（策略改为独立聚合）。`only-danmuku/only-danmuku-domain/src/main/kotlin/edu/only4/danmuku/domain/aggregates/video_hls_quality_auth/VideoHlsQualityAuth.kt`

## 命令影响
### 需要更新/新增
- 新增处理聚合命令（设计稿）：`StartVideoPostProcessing` / `ApplyVideoPostProcessingTranscodeResult` / `ApplyVideoPostProcessingEncryptResult` / `SyncVideoPostProcessStatus` / `SyncVideoFilePostFromProcessing`。`only-danmuku/iterate/video-post-processing-workflow/video_post_processing_workflow_gen.json`
- 加密命令入参调整为 `videoPostId + fileIndex`。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/video_encrypt/GenerateVideoHlsQualityKeysCmd.kt`
- 新增转正绑定命令：`BindVideoHlsEncryptKeyToVideo` / `BindVideoHlsKeyTokenToVideo`（补充 `videoId`）。`only-danmuku/iterate/video-post-processing-workflow/video_post_processing_workflow_gen.json`
- 新增策略设置命令：`SetVideoQualityPolicy`（转正后配置）。`only-danmuku/iterate/video-post-processing-workflow/video_post_processing_workflow_gen.json`
- 批量加密结果回写依赖字段变更，需要同步调整。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/video_encrypt/PersistVideoEncryptBatchResultCmd.kt`
- 生产态落库命令若读取 `video_file_post` 字段，需要同步调整。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/video/TransferVideoToProductionCmd.kt`

### 后续无用/建议下线
- `CreateVideoFilePostCmd`：旧的稿件文件写路径。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/video_file_post/CreateVideoFilePostCmd.kt`
- `UpdateVideoFilePostTranscodeResultCmd`：由处理聚合回填替代。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/video_file_post/UpdateVideoFilePostTranscodeResultCmd.kt`
- `DeleteVideoFilePostCmd`：改为 VideoPost 内部子实体更新。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/video_file_post/DeleteVideoFilePostCmd.kt`
- `RefreshVideoPostTranscodeStatusCmd`：由处理聚合完成事件统一同步。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/video_post/RefreshVideoPostTranscodeStatusCmd.kt`

## 事件影响
### 需要新增/调整
- 新增处理聚合事件（设计稿）：`VideoPostProcessingStarted` / `VideoPostProcessingTranscodeCompleted` / `VideoPostProcessingCompleted`。`only-danmuku/iterate/video-post-processing-workflow/video_post_processing_workflow_gen.json`
- `VideoPostProcessingCompleted` 订阅中补充回填 `video_file_post` 子实体与分辨率档位。`only-danmuku/iterate/video-post-processing-workflow/video-post-processing-workflow.md`
- `VideoAuditPassedDomainEvent` 订阅中补充密钥/Token 绑定与策略设置编排。`only-danmuku/iterate/video-post-processing-workflow/video-post-processing-workflow.md`

### 后续无用/建议下线
- `VideoFilePostCreatedDomainEvent`：子实体不再发布领域事件。`only-danmuku/only-danmuku-domain/src/main/kotlin/edu/only4/danmuku/domain/aggregates/video_file_post/events/VideoFilePostCreatedDomainEvent.kt`
- `VideoFilePostDeletedDomainEvent`：子实体不再发布领域事件。`only-danmuku/only-danmuku-domain/src/main/kotlin/edu/only4/danmuku/domain/aggregates/video_file_post/events/VideoFilePostDeletedDomainEvent.kt`
- `VideoFilePostTranscodeResultUpdatedDomainEvent`：子实体不再发布领域事件。`only-danmuku/only-danmuku-domain/src/main/kotlin/edu/only4/danmuku/domain/aggregates/video_file_post/events/VideoFilePostTranscodeResultUpdatedDomainEvent.kt`
- `VideoFilePostCreatedDomainEventSubscriber`：旧事件订阅链路。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/subscribers/domain/video_file_post/VideoFilePostCreatedDomainEventSubscriber.kt`
- `VideoFilePostDeletedDomainEventSubscriber`：旧事件订阅链路。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/subscribers/domain/video_file_post/VideoFilePostDeletedDomainEventSubscriber.kt`
- `VideoFilePostTranscodeResultUpdatedDomainEventSubscriber`：旧事件订阅链路。`only-danmuku/only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/subscribers/domain/video_file_post/VideoFilePostTranscodeResultUpdatedDomainEventSubscriber.kt`
