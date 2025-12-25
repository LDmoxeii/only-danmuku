# 视频加密：清晰度独立 Key 迭代说明

## 一、背景与目标
- 现有加密为“单 key + 全量加密”，无法做到清晰度级别的密钥隔离。
- 业务需要“低清公开、高清付费”的策略可真正落地，必须做到**不同清晰度使用独立 key**。
- 本迭代在 OSS 存储已落地的基础上推进，明文与加密产物均存 OSS。
- 目标：每个 quality 独立 key，同一稿件在同一批次使用统一 keyVersion；token 能覆盖多清晰度。

## 二、范围
1) Key 生成：按清晰度批量生成 key（quality 绑定），同批次统一 keyVersion。  
2) 加密编排：按清晰度加密并生成 `enc/` 产物，最后生成加密 master。  
3) Token 语义调整：token 不再绑定单一 keyId，按 fileId+keyVersion 发放并校验 allowedQualities。  
4) 轮换：批量轮换所有清晰度 key，重加密并吊销旧 key。  
5) 接口与查询：按清晰度获取 key，master/playlist 输出仍由后端过滤清晰度。  
不在本迭代：DRM、按分片滚动 key、前端直传 OSS 分片上传。

## 三、设计要点
### 3.1 Key 模型
- `video_hls_encrypt_key` 必须与 `quality` 绑定（不再使用 `quality = null` 作为全局 key）。  
- `keyVersion` 作为“批次版本”，同一稿件所有清晰度共享同一 `keyVersion`。  
- `keyUriTemplate` 建议写入 `quality`，例如：  
  `/video/enc/key?keyId={keyId}&quality={quality}&token=__TOKEN__`

### 3.2 Token 模型
- token 绑定 `fileId + keyVersion + allowedQualities`，**不再绑定 keyId**。  
- `ConsumeVideoHlsKeyToken` 校验 allowedQualities，并按 `fileId + keyVersion + quality + keyId` 获取对应 key。  
- 好处：同一 token 可访问多个清晰度（由 allowedQualities 控制），避免“每档位一个 token”。

### 3.3 加密编排
```
ABR 成功 -> List ABR qualities
        -> GenerateVideoHlsQualityKeysCmd (批量生成 key)
        -> EncryptHlsWithQualityKeysCli (按清晰度加密 + 生成 enc/master)
        -> PersistVideoEncryptBatchResultCmd (写 encrypt_status/keyVersion/enc path)
```
- 避免“每个 key 触发一次加密”的并发冲突，统一由批量编排驱动。  
- `enc/` 目录结构：
```
{filePath}/enc/
├── master.m3u8
├── 1080p/index.m3u8
├── 1080p/seg_00001.ts.enc
└── 720p/...
```

### 3.4 master/playlist 输出
- master.m3u8 仍包含全部清晰度，接口层按 allowedQualities 过滤后返回。  
- 各清晰度 playlist 内写入对应 keyId + quality 参数，保证取 key 时可正确校验。

### 3.5 轮换
- 轮换采用“全量批次”：对所有清晰度生成新 keyVersion，吊销旧 key。  
- 轮换后重新加密并生成新 enc 目录。旧 token 因 keyVersion 不匹配自动失效。

### 3.6 失败与回滚
- 任一清晰度加密失败即标记 FAILED，保留明文 ABR 作为回退。  
- 支持重复发起批量加密，幂等点以 keyVersion 控制。

### 3.7 迁移策略
- 已加密但为“单 key”版本的稿件，需要重新生成清晰度 key 并重加密。  
- 可通过管理端批量触发或按播放请求惰性重加密。

## 四、影响面清单
### 4.1 接口（API）
- `/video/enc/token`、`/admin/video/enc/token`（token 不再绑定 keyId）  
- `/video/enc/videoResource/*`（master/playlist 输出保持，但内部 keyUri 含 quality）  
- `/video/enc/key`、`/admin/video/enc/key`（取 key 时按质量校验）  

### 4.2 命令（Cmd）
- 新增：`GenerateVideoHlsQualityKeysCmd`（批量生成）  
- 新增：`PersistVideoEncryptBatchResultCmd`（批量结果落库）  
- 调整：`RotateVideoHlsKeyCmd` → 批量轮换所有清晰度  
- 调整：`IssueVideoHlsKeyTokenCmd` / `ConsumeVideoHlsKeyTokenCmd`（token 语义变更）

### 4.3 CLI（适配层）
- 新增：`EncryptHlsWithQualityKeysCli`（批量加密并生成 master）

### 4.4 查询（Qry）
- 新增：`GetLatestVideoHlsKeyVersionQry`  
- 新增：`ListVideoHlsEncryptKeysQry`（按 fileId + keyVersion 返回质量 key 列表）

## 五、关键流程
```
ABR 成功 -> 生成质量列表 -> 批量生成 key -> 批量加密 -> 持久化结果 -> 对外提供播放
```

## 六、数据库变更（如需要）
- `video_hls_key_token.key_id` 删除，token 按 `fileId + keyVersion` 绑定。

## 七、交付物
- 设计文档：`iterate/video-encrypt-quality-key/video-encrypt-quality-key.md`  
- DDD 设计 JSON：`iterate/video-encrypt-quality-key/video_encrypt_quality_key_gen.json`  
- 数据库脚本：`iterate/video-encrypt-quality-key/video_encrypt_quality_key_update.sql`
