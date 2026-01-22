# 视频稿件处理-单分辨率加密重构迭代方案

## 目标与边界
- 目标：把“分P级别加密”细化为“分辨率（清晰度）级别加密”，让处理状态可以在清晰度维度独立跟踪，避免批量加密一次失败影响整文件。
- 约束：事件仅由聚合根发布；命令只加载/保存聚合；ACL 仅处理路径/对象前缀；查询可由监听器编排。
- 范围：重构处理链路与加密链路；不包含播放侧 API 与进度查询 API 的新需求。

## 领域模型调整
### VideoPostProcessingVariant（子实体）
- 新增字段：
  - transcodeStatus: ProcessStatus
  - encryptStatus: ProcessStatus
  - encryptFailReason: String?
- 规则：
  - 转码完成后，为生成的每个档位设置 transcodeStatus=SUCCESS、encryptStatus=PENDING。
  - 单档位加密完成后更新 encryptStatus=SUCCESS/FAILED。

### VideoPostProcessingFile / VideoPostProcessing（聚合根状态汇总）
- 由聚合根根据 variant 状态汇总 file/aggregate 的 transcodeStatus、encryptStatus：
  - encryptStatus = SUCCESS 当且仅当该 file 的所有 variant encryptStatus 为 SUCCESS/SKIPPED。
  - encryptStatus = FAILED 当任一 variant FAILED。
  - encryptStatus = PROCESSING 当仍有 PENDING/PROCESSING。
- 文件级别加密完成时，聚合根发布 `VideoPostProcessingFileEncryptCompletedDomainEvent`。

### VideoHlsEncryptKey（根）
- 新增单档位密钥生成命令：`GenerateVideoPostQualityKeyCmd`。
- 生成完成后由聚合根发布 `VideoPostQualityKeyGeneratedDomainEvent`，驱动单档位加密。
- keyVersion 策略：同一 file 的一个处理批次内保持一致。
  - 方案：在转码完成时用 `PrepareVideoPostProcessingEncryptContextCmd` 分配 keyVersion 并固化到 VideoPostProcessingFile，后续单档位命令复用该 keyVersion。

## 新增/调整命令与事件
- `PrepareVideoPostProcessingEncryptContextCmd`：为某 file 分配 keyVersion 并返回输出目录/前缀。
- `GenerateVideoPostQualityKeyCmd`：单清晰度 key 生成（替代批量命令）。
- `VideoPostQualityKeyGeneratedDomainEvent`：携带 key 与清晰度，触发单档位加密。
- `ApplyVideoPostProcessingVariantEncryptResultCmd`：更新 variant 的 encryptStatus，并触发 file/aggregate 状态汇总。
- `VideoPostProcessingFileEncryptCompletedDomainEvent`：文件内全部清晰度加密完成，触发生成加密 master。

## 防腐层（ACL）调整
- `EncryptHlsVariantWithKeyCli`：单清晰度加密。
  - 只下载 `sourceDir/<quality>/`，只上传 `outputDir/<quality>/`。
  - 禁止清空 output 前缀、禁止重写 master。
- `GenerateEncryptedMasterByVariantsCli`：根据档位列表生成加密 master.m3u8 并上传到 outputDir 根目录。

## 关键流程（简述）
1) `VideoPostProcessingTranscodeCompletedDomainEvent` -> 解析 variants。
2) `PrepareVideoPostProcessingEncryptContextCmd` 分配 keyVersion、拿到输出路径。
3) 逐档位发送 `GenerateVideoPostQualityKeyCmd(videoPostId, fileIndex, quality, keyVersion)`。
4) `VideoPostQualityKeyGeneratedDomainEvent` -> 查询加密上下文 -> `EncryptHlsVariantWithKeyCli`。
5) `ApplyVideoPostProcessingVariantEncryptResultCmd` 更新 variant 状态并汇总 file/aggregate。
6) 当 file 内所有 variant encryptStatus 完成 -> `VideoPostProcessingFileEncryptCompletedDomainEvent` -> `GenerateEncryptedMasterByVariantsCli`。
7) 当所有 file 加密完成 -> 处理聚合完成事件 -> 同步稿件状态与文件产物。

## SQL 变更（如需）
- `video_post_processing_variant`：新增 transcode_status / encrypt_status / encrypt_fail_reason。
- `video_post_processing_file`：新增 encrypt_method / encrypt_key_version（用于批次 keyVersion 固化）。

## 兼容性与弃用
- `GenerateVideoPostQualityKeysCmd`（批量）与 `EncryptHlsWithQualityKeysCli`（批量）进入弃用期，逐步替换为单档位命令 + master 合成。
- `VideoPostProcessingTranscodeCompletedDomainEventSubscriber` 由“批量加密”改为“逐档位加密”。

## 风险与注意
- 并发加密时，必须避免 output 前缀全量删除。
- master 的生成只能在全部档位完成后执行，避免被部分档位覆盖。
- keyVersion 需在一个 file 的处理批次内保持一致，避免播放侧版本混乱。
