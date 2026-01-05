# 保留 video_file_post 并字段优化迁移方案（无数据版本）

## 目标与范围
- `video_file_post` 作为 `video_post` 子实体，用于稿件审核前的文件查询（允许按 ID 查询）。
- 在 `VideoPostProcessingCompletedDomainEvent` 后回填 `video_file_post` 与 `video_file_post_variant`（转码/加密产物摘要 + 分辨率档位）。
- `video_hls_abr_variant`/`video_hls_quality_auth` 迁移为 `video_post_processing_variant` 与 `video_file_post_variant`（授权策略不在处理流程保存）。
- `video_hls_encrypt_key`/`video_hls_key_token` 处理期使用 `video_post_id + file_index`，转正后补 `video_id`，避免用路径做关联键。
- `video_file_post` 字段优化：`video_id` 更名为 `video_post_id`，新增产物摘要字段，删除处理阶段冗余字段。

## 迁移步骤（无数据）
1) 更新设计与流程：
   - 新增 `SyncVideoFilePostFromProcessingCmd`（VideoPost 聚合内）与完成事件订阅回填逻辑。
   - 聚合图中移除 `video_hls_quality_auth`，新增 `video_post_processing_variant` 与 `video_file_post_variant` 子实体。
2) 执行 SQL 变更：
   - 处理聚合与子表：`iterate/video-post-processing-workflow/video_post_processing_workflow_update.sql`。
   - video_file_post 字段优化与旧表清理：`iterate/video-post-processing-workflow/video_post_processing_video_file_post_optimize.sql`。
3) 更新领域层：
   - 为处理聚合新增 `VideoPostProcessingVariant`。
   - `VideoFilePost` 作为 VideoPost 子实体，`video_id` 更名 `videoPostId`，新增产物摘要字段，删除 ABR 档位集合与处理阶段字段。
   - 新增 `VideoFilePostVariant` 子实体（分辨率档位）。
4) 更新应用层：
   - 新增 `SyncVideoFilePostFromProcessingCmd` 与 `ListVideoPostProcessingFilesForSync`。
   - 移除/替换 `UpdateVideoFilePostTranscodeResultCmd`、`VideoFilePostCreatedDomainEventSubscriber`。
5) 更新查询与 API：
   - ABR 档位/清晰度授权查询改读 `video_post_processing_*` 子表。
   - 若仍需按 `videoFilePostId` 查询，保留 query 不变；写路径统一使用 `videoPostId + fileIndex`。

## SQL 脚本
- `iterate/video-post-processing-workflow/video_post_processing_workflow_update.sql`
- `iterate/video-post-processing-workflow/video_post_processing_video_file_post_optimize.sql`

## 代码改造清单（按层归类）

### Domain
- 新增处理聚合子实体：
  - `only-danmuku-domain/src/main/kotlin/edu/only4/danmuku/domain/aggregates/video_post_processing/VideoPostProcessingVariant.kt`
- 调整 VideoFilePost：
  - 新增 `videoPostId` 与产物摘要字段（outputPrefix/variants/keyVersion）。
  - 删除 `videoHlsAbrVariants` 子实体集合与对应 `VideoHlsAbrVariant` 实体。
  - 新增 `VideoFilePostVariant` 子实体集合。
  - 移除处理阶段字段（`abr_source_*`、`encrypt_key_id`、`encrypt_fail_reason`、`update_type`）。
  - 新增 `syncFromProcessing(...)` 行为替代原 `applyTranscodeResult` 写路径。
- 调整加密聚合键：
  - `VideoHlsEncryptKey`/`VideoHlsKeyToken` 处理期使用 `videoPostId + fileIndex`，转正后补 `videoId`。

### Application
- 新增命令/查询：
  - `commands/video_post/SyncVideoFilePostFromProcessingCmd.kt`
  - `queries/video_post_processing/ListVideoPostProcessingFilesForSync.kt`
- 替换/下线：
  - `commands/video_file_post/UpdateVideoFilePostTranscodeResultCmd.kt`
  - `subscribers/domain/video_file_post/VideoFilePostCreatedDomainEventSubscriber.kt`
- 若仍存在依赖 `VideoHlsAbrVariant` 的查询 Handler，改读 `video_file_post_variant`。

### Adapter
- 调整查询 Handler：
- ABR 档位读取 `video_file_post_variant`，清晰度授权改为分辨率策略聚合。
- 完成事件订阅器：
  - `VideoPostProcessingCompletedDomainEvent` 订阅中追加回填 `video_file_post` 子实体的命令调用。

### Queries/DTO
- 更新 `VideoFilePost` 查询模型：
  - 新增 `videoPostId` 与产物摘要字段。
  - 删除 `abrSource*`/`encryptKeyId`/`encryptFailReason`/`updateType` 字段。

### Schema/Meta
- 新增 `video_post_processing_variant` 与 `video_file_post_variant` 元信息。
- 更新 `video_file_post` 元信息（新增/删除字段）。

## 注意事项
- 由于无数据，可直接 DROP/ALTER。
- `video_file_post` 是 VideoPost 子实体，命令入参使用 `videoPostId + fileIndex`，对外查询可保留 `videoFilePostId`。
- 密钥/Token 转正绑定应由审核运行时的订阅器编排完成（先查询 ID，再逐条命令补 `videoId`）。
