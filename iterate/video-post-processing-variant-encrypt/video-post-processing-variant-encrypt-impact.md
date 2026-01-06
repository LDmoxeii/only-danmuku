# 单分辨率加密重构 - 影响范围与弃用清单

## 需要更新/新增
### 领域层（domain）
- `VideoPostProcessingVariant`：新增 `transcodeStatus` / `encryptStatus` / `encryptFailReason` 字段与状态更新逻辑。
- `VideoPostProcessingFile` / `VideoPostProcessing`：基于 variant 状态汇总 file/aggregate 状态，新增文件加密完成事件发布。
- `VideoHlsEncryptKey`：生成单档位 key 后发布 `VideoPostQualityKeyGeneratedDomainEvent`。

### 应用层（application）
- 新增命令：
  - `PrepareVideoPostProcessingEncryptContextCmd`
  - `GenerateVideoPostQualityKeyCmd`
  - `ApplyVideoPostProcessingVariantEncryptResultCmd`
- 新增订阅：
  - `VideoPostQualityKeyGeneratedDomainEventSubscriber`
  - `VideoPostProcessingFileEncryptCompletedDomainEventSubscriber`
- 调整订阅：
  - `VideoPostProcessingTranscodeCompletedDomainEventSubscriber` 从批量加密改为按档位发起加密。
- 新增查询：
  - `GetVideoPostProcessingEncryptContextQry`
  - `ListVideoPostProcessingVariantsForEncryptMasterQry`

### 防腐层（ACL）
- 新增 CLI：
  - `EncryptHlsVariantWithKeyCli`
  - `GenerateEncryptedMasterByVariantsCli`

### SQL/模型
- `video_post_processing_variant`：新增状态字段。
- `video_post_processing_file`：新增 `encrypt_method` / `encrypt_key_version`。

## 可弃用/替换
- `GenerateVideoPostQualityKeysCmd`：批量生成 key（替换为单档位命令）。
- `EncryptHlsWithQualityKeysCli`：批量加密（替换为单档位加密 + master 合成）。
- `ApplyVideoPostProcessingEncryptResultCmd`：文件级加密结果回写（替换为 variant 级回写）。
- `VideoPostProcessingTranscodeCompletedDomainEventSubscriber` 中的批量加密逻辑。

## 行为变化摘要
- 加密过程由“分P批量加密”变为“清晰度逐档位加密”。
- 文件级/聚合级状态由清晰度状态汇总得到。
- master.m3u8 在所有清晰度加密完成后生成，避免覆盖与并发冲突。
