# 视频稿件聚合统一化迭代说明

## 一、现状分析
### 1.1 聚合拆分与职责
1) VideoPost（稿件信息聚合）
- 责任：稿件基础信息、互动设置、审核状态、转码状态汇总、删除。
- 核心功能：创建草稿、编辑基础信息、审核通过/失败、互动设置变更、删除稿件。
- 相关命令（cmd）：CreateVideoPostCmd、UpdateVideoPostCmd、AuditVideoPostCmd、ChangeVideoPostInteractionCmd、DeleteVideoPostCmd、RefreshVideoPostTranscodeStatusCmd、TranscodeAllTranscodingFilesCmd、RecordVideoAuditTraceCmd。
- 事件（有逻辑的订阅）：
  - VideoAuditPassedDomainEvent：奖励作者、转入生产库、清理临时文件。
  - VideoPostDeletedDomainEvent：删除生产视频、扣减作者收益、删除物理资源。
  - VideoPostInteractionChangedDomainEvent：同步互动设置到生产视频。
  - VideoPostTranscodingRequiredDomainEvent：订阅存在但当前无触发点（未在聚合内挂载）。

2) VideoFilePost（稿件文件聚合）
- 责任：分P文件元数据、转码状态、ABR档位、加密状态、文件路径。
- 核心功能：创建分P、转码结果回写、删除分P、记录ABR档位。
- 相关命令（cmd）：CreateVideoFilePostCmd、UpdateVideoFilePostTranscodeResultCmd、DeleteVideoFilePostCmd。
- 相关查询（qry）：GetVideoFilePostPathQry、ListVideoAbrVariantsQry。
- 事件（有逻辑的订阅）：
  - VideoFilePostCreatedDomainEvent：合并分片 -> 转码 -> 上传OSS -> 回写转码结果。
  - VideoFilePostTranscodeResultUpdatedDomainEvent：刷新稿件转码状态；触发生成Key + 加密 + 回写加密结果。
  - VideoFilePostDeletedDomainEvent：刷新稿件转码状态。

3) VideoHlsEncryptKey（HLS密钥聚合）
- 责任：按清晰度存储Key、Key版本、密钥状态、Key URI模板。
- 核心功能：批量生成质量Key、轮换Key、按版本查询Key列表。
- 相关命令（cmd）：GenerateVideoHlsQualityKeysCmd、RotateVideoHlsKeyBatchCmd、PersistVideoEncryptBatchResultCmd。
- 相关查询（qry）：GetLatestVideoHlsKeyVersionQry、ListVideoHlsEncryptKeysQry、GetVideoEncryptStatusQry、UniqueVideoHlsEncryptKeyQry。
- 事件（有逻辑的订阅）：
  - VideoHlsEncryptKeyCreatedDomainEvent：为对应 quality 自动写入默认清晰度授权策略。

4) VideoHlsKeyToken（播放Token聚合）
- 责任：签发/消费短时Token，记录可用次数与过期状态。
- 相关命令（cmd）：IssueVideoHlsKeyTokenCmd、ConsumeVideoHlsKeyTokenCmd。
- 相关查询（qry）：UniqueVideoHlsKeyTokenQry。
- 事件：无。

5) VideoHlsQualityAuth（清晰度授权聚合）
- 责任：按 fileId + quality 存储授权策略。
- 相关命令（cmd）：UpsertVideoHlsQualityAuthCmd。
- 相关查询（qry）：UniqueVideoHlsQualityAuthQry。
- 事件：无。

### 1.2 现有事件驱动链路
- 转码链路：VideoFilePostCreated -> MergeUploadToMp4Cli -> TranscodeVideoFileToAbrCli -> UploadVideoAbrOutputCli -> UpdateVideoFilePostTranscodeResultCmd。
- 加密链路：VideoFilePostTranscodeResultUpdated -> GenerateVideoHlsQualityKeysCmd -> EncryptHlsWithQualityKeysCli -> PersistVideoEncryptBatchResultCmd。
- 授权链路：VideoHlsEncryptKeyCreated -> UpsertVideoHlsQualityAuthCmd。
- 稿件侧链路：VideoPostDeleted/InteractionChanged/VideoAuditPassed -> 对生产视频与奖励/清理的联动。

### 1.3 现状问题
- 关键流程跨多个聚合强耦合（VideoPost / VideoFilePost / VideoHlsEncryptKey / Token / Auth）。
- 事件驱动导致流程分散，生命周期难统一（转码、加密、授权依赖事件链）。
- 现有接口大量使用 videoFilePostId 等“分散聚合 ID”；若改为单聚合根后，这些 ID 将不再对外可见，需统一对外定位方式。
- 若改为单聚合，现有事件订阅将失效，需要新编排方式。

## 二、改造可行性分析
### 2.1 可行点
- VideoPost 本身已持有“稿件全生命周期”语义，天然适合作为聚合根。
- VideoFilePost / VideoHlsEncryptKey / Token / Auth 数据都以 fileId/videoId 关联，迁移为子实体仍可维持数据结构。
- 现有状态字段可用于 job 扫描（transfer_result / encrypt_status / update_type），无需立刻改表。

### 2.2 需要解决的约束
- 子实体 ID 不允许对外暴露：现有接口/命令大量使用 videoFilePostId。
- 子实体不允许发出事件：现有转码/加密/授权事件链必须迁移为 job 编排。
- CQRS 约束：Cmd 不能调用 Qry/CLI；Cmd 必须通过 Repository 整存整取聚合。
- 事务边界扩大：聚合内实体变化需要统一保存，避免并发更新冲突。

## 三、具体实现技术方案
### 3.1 聚合边界重划
以 VideoPost 作为聚合根，聚合内仍保持“文件维度/清晰度维度”的业务语义：
- VideoFilePost（含 VideoHlsAbrVariant）
- VideoHlsEncryptKey：单文件 + 单清晰度唯一
- VideoHlsKeyToken：单文件级别（覆盖该文件的多清晰度）
- VideoHlsQualityAuth：单文件 + 单清晰度策略

约束落地：
- 外部接口只使用 videoPostId + fileIndex（或业务可识别字段）定位分P。
- filePostId 仅在聚合内部使用，不再作为 API/命令输入参数。
- 所有子实体的事件发布移除，改由 root 或 job 编排触发。

### 3.2 命令调整策略
1) CreateVideoPostCmd 扩展：
- 入参增加 uploadFileList（包含 uploadId、fileIndex、fileName、fileSize、duration）。
- 创建 VideoPost 同时创建子实体 VideoFilePost，初始 transfer_result=TRANSCODING。
- 删除/废弃 CreateVideoFilePostCmd 的外部使用入口。

2) UpdateVideoFilePostTranscodeResultCmd / GenerateVideoHlsQualityKeysCmd / PersistVideoEncryptBatchResultCmd：
- 这些命令以“子聚合”为入口，不再符合整存整取约束，将被替换为聚合根命令。
- 替换为：
  - GenerateVideoPostQualityKeysCmd：加载 VideoPost 聚合并生成 key，返回 keysJson + keyVersion。
  - ApplyVideoPostTranscodeResultCmd：接收 CLI 产物并落库（仅聚合状态变更）。
  - ApplyVideoPostEncryptResultCmd：接收 CLI 产物并落库（仅聚合状态变更）。
- Cmd 内只做聚合变更，不调用 Qry/CLI。

3) UpsertVideoHlsQualityAuth：
- 不再通过 VideoHlsEncryptKeyCreated 事件触发，改为“生成 key 时同步写入默认策略”，由 ProcessVideoPostEncryptCmd 内聚合逻辑完成。

### 3.3 Job 编排方案（替代事件链）
Job A：TranscodeVideoPostJob（转码）
```
ListTranscodingVideoPostsQry
  -> foreach (videoPostId + fileIndex + uploadId)
     -> GetUploadSessionTempPathQry
     -> MergeUploadToMp4Cli
     -> FFprobe 获取源信息
     -> TranscodeVideoFileToAbrCli
     -> UploadVideoAbrOutputCli
     -> ApplyVideoPostTranscodeResultCmd (Repository: VideoPost)
```
触发条件：transfer_result=TRANSCODING 且 update_type=HAS_UPDATE（或新建时默认 HAS_UPDATE）。

Job B：EncryptVideoPostJob（加密）
```
ListEncryptPendingVideoPostsQry
  -> foreach (videoPostId + fileIndex)
     -> GenerateVideoPostQualityKeysCmd (Repository: VideoPost)
     -> EncryptHlsWithQualityKeysCli
     -> ApplyVideoPostEncryptResultCmd (Repository: VideoPost)
```
触发条件：transfer_result=SUCCESS 且 encrypt_status=UNENCRYPTED。

Job C：StatusRefreshJob（可选）
- 对处于 TRANSCODING 的 VideoPost 汇总子文件状态，纠偏状态与总时长。

### 3.4 并发与幂等
- Job 领任务时使用 “状态+update_type” 作为轻量锁：
  - 先将 update_type 从 HAS_UPDATE -> NO_UPDATE 再执行，避免多实例重复处理。
- 失败场景写入 failReason，并将 update_type 置回 HAS_UPDATE 允许重试。
- 加密/转码 CLI 需可重复执行（幂等以输出目录 + keyVersion 管控）。

### 3.5 API 与参数调整
- 公开接口改为使用 videoPostId + fileIndex（或明确的质量/资源路径）。
- keyId/quality 仍作为播放链路的对外标识。
- videoFilePostId 不再对外暴露（仅内部聚合使用）。

## 四、影响面清单
### 4.1 将被弱化/废弃的事件订阅
- VideoFilePostCreatedDomainEventSubscriber
- VideoFilePostTranscodeResultUpdatedDomainEventSubscriber
- VideoFilePostDeletedDomainEventSubscriber
- VideoHlsEncryptKeyCreatedDomainEventSubscriber

### 4.2 仍保留的 VideoPost 事件订阅
- VideoAuditPassedDomainEventSubscriber
- VideoPostDeletedDomainEventSubscriber
- VideoPostInteractionChangedDomainEventSubscriber

### 4.3 核心命令变化
- 扩展：CreateVideoPostCmd（增加 uploadFileList）
- 内部化：CreateVideoFilePostCmd（仅聚合内部使用或逐步移除）
- 替换：UpdateVideoFilePostTranscodeResultCmd / GenerateVideoHlsQualityKeysCmd / PersistVideoEncryptBatchResultCmd
  -> ApplyVideoPostTranscodeResultCmd / GenerateVideoPostQualityKeysCmd / ApplyVideoPostEncryptResultCmd
- 新增：转码/加密的候选查询（ListTranscodingVideoPostsQry / ListEncryptPendingVideoPostsQry）

## 五、数据库变更
无必须的表结构变更。  
可选优化索引（如需加速 job 扫描）：
- video_file_post(transfer_result, encrypt_status, update_type, video_id)

## 六、交付物
- 设计文档：iterate/video-post-aggregate-unify/video-post-aggregate-unify.md  
- DDD 设计 JSON：iterate/video-post-aggregate-unify/video_post_aggregate_unify_gen.json  
- SQL 变更：iterate/video-post-aggregate-unify/video_post_aggregate_unify_update.sql
