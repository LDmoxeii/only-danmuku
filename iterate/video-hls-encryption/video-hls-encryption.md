# 视频 HLS AES-128 加密迭代设计（固定密钥本地存储）

## 背景与目标
现有视频转码流程未对 HLS 切片进行保护。为满足基础防盗链需求，本迭代为每个视频生成固定的 AES-128 密钥，存储在本地（随视频独立的密钥文件），再用该密钥进行 HLS 加密输出。密钥可通过内部接口/本地文件被播放器或 CDN 按需拉取。

## 范围
- 转码输出：新增“加密 HLS”流程，支持 AES-128 固定密钥，密钥文件与 m3u8/ts 同目录。
- 密钥管理：为每个视频生成/持久化一条密钥记录；提供查询/读取（内部用）能力。
- 触发链路：视频审核通过后继续执行加密转码（代替或补充现有未加密 HLS）。

## 约束
- 加密算法：AES-128，固定 key/IV 每视频一份；密钥以本地文件 + DB 记录存储，不接入外部 KMS。
- 播放端取 key 的方式（API/本地文件）可配置；本迭代先提供内部接口读取明文 key。
- 不修改已有非加密转码输出（如有），加密 HLS 作为新的输出版本。

## 设计
### 数据模型
- 新表 `video_hls_key`（见 SQL）：
  - `video_post_id`：视频稿件 ID（唯一）。
  - `secret_key`：密钥（Base64）。
  - `iv`：可选 IV（Base64）；若未生成则写空。
  - `key_path`：密钥文件绝对/相对路径（用于 m3u8 中的 key URI）。
  - `algorithm`：固定 `AES-128`。
  - 审计字段：create/update/deleted。
  - 索引：`uk_video_hls_key_video` 唯一约束，`idx_video_hls_key_path`。

### 领域/应用元素
- 命令：
  - `GenerateHlsKeyCmd`：为 video_post 生成并落库密钥，落地 key 文件。
  - `TranscodeVideoToEncryptedHlsCmd`：使用密钥生成 HLS（m3u8/ts）并写入存储路径。
- 事件：
  - `VideoHlsKeyGeneratedDomainEvent`（aggregate: VideoHlsKey）
  - `VideoEncryptedHlsGeneratedDomainEvent`（aggregate: VideoFile/VideoPost）
- 查询：
  - `GetHlsKeyByVideoPostQry`：内部查询密钥/路径。

### 流程
1) 审核通过后转码（加密 HLS）：
```
VideoAuditTraceRecordedDomainEvent
  -> GenerateHlsKeyCmd
  -> VideoHlsKeyGeneratedDomainEvent
  -> TranscodeVideoToEncryptedHlsCmd (依赖 key_path/secret_key)
  -> VideoEncryptedHlsGeneratedDomainEvent
```
2) Key 获取（内部接口/本地文件）：
```
Client (player/CDN) -> GetHlsKeyByVideoPostQry -> 返回 key 或根据 key_path 读取文件
```

### 本地文件规范
- 路径建议：`/data/hls/{videoPostId}/key.key`，m3u8 中引用相对路径 `key.key`。
- m3u8 片段/密钥应同目录，以减少跨域/鉴权复杂度。

## 交付物
- 需求/设计：`iterate/video-hls-encryption/video-hls-encryption.md`
- SQL：`iterate/video-hls-encryption/video_hls_encryption_update.sql`
- DDD 设计 JSON：`iterate/video-hls-encryption/video_hls_encryption_gen.json`
- 流程图：
  - `Video_audit_to_encrypted_hls.mmd`
  - `Video_hls_key_fetch.mmd`

## 后续/风险
- 固定 key 安全性有限，建议后续升级为动态 token/临时 URL。
- 如需 CDN 对 key 做鉴权，可在后续迭代增加防腐层适配。
