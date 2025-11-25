# 视频加密 / 解密（HLS AES-128）设计说明

## 一、背景与目标
- 现有视频转码/ABR 输出为明文 HLS，付费/私有内容存在泄露风险，无法满足“防盗链 + 按用户授权发放密钥”的需求。
- 目标：在现有 ABR 链路之后追加“HLS AES-128 加密 + 密钥管理 + 解密发放”能力，支持**分辨率分级授权**（例：360/480 匿名可看，720/1080 需登录/付费）。
- 保持对现有明文播放的兼容，允许按稿件配置是否加密；支持后续密钥轮换/吊销。

## 二、范围
1) 输出形态：为 ABR 产物生成一套“加密版” master/variant m3u8 + ts 片段（独立目录），与明文版共存。  
2) 加密方式：HLS AES-128（CBC），单稿件单密钥，支持 IV 固定或随分片递增。  
3) 授权与解密：签发短时 token → 前台/后台通过 m3u8 自动携带 token 访问 `/video/enc/key` 获取密钥 → 播放器完成解密。  
4) 运维：支持重新加密（重新生成密钥并重写 m3u8）、失败回滚、状态查询。  
不在本迭代：DRM（FairPlay/Widevine）、硬件级密钥保护、下载离线解密。

## 三、设计要点
### 3.1 加密输出与目录
```
/video/{customerId}/{videoId}/{fileIndex}/
├── master.m3u8           # 明文（原 ABR）
├── 720p/...              # 明文
└── enc/                  # 新增：加密版
    ├── master.m3u8       # 带 #EXT-X-KEY 占位
    ├── 720p/index.m3u8   # 带 #EXT-X-KEY 占位
    ├── 720p/0000.ts.enc  # AES-128 密文
    └── ...               # 其他清晰度
```
- `enc/master.m3u8` / `enc/{quality}/index.m3u8` 的 `URI` 写入占位符 `__TOKEN__`，接口层在下发时动态替换为真实 token（示例 URI：`/video/enc/key?keyId={keyId}&token=__TOKEN__`）。  
- 加密后 ts 文件扩展名改为 `.ts.enc`（播放器可接受，或通过 m3u8 内的 URI 指向实际文件名）。

### 3.2 加密链路（单 fileId）
```
ABR 成功 → 是否需要加密?
        ↓是
GenerateVideoHlsKeyCli（随机 AES-128 key + IV，占位 keyUri）
        ↓
EncryptHlsWithKeyCli（读取明文 ABR 输出 → 生成 enc/ 目录 + 占位 URI）
        ↓
PersistVideoEncryptResultCmd（写 encrypt 状态、密钥元数据、enc 目录）
        ↓
下发 m3u8 时签发 token，替换 __TOKEN__ → 播放器请求 key + ts 解密播放
```
- 失败/中断：`encrypt_status` 置 FAILED，保留明文 ABR 作为回退。  
- 重新加密：生成新 keyVersion，覆盖 `enc/` 目录，旧 token 失效。

### 3.3 分辨率分级授权
- 授权粒度：到 quality（1080p/720p/480p/360p），支持“低清晰度匿名，高清晰度需登录/付费”。
- 配置存储：新增 `video_hls_quality_auth` 记录 fileId + quality + auth_policy（枚举：PUBLIC/LOGIN/PAID/CUSTOM）。
- master 下发策略：按用户态生成 master.m3u8，仅包含用户被授权的 quality；
- Key 策略：`video_hls_encrypt_key` 可按 quality 绑定独立 key；token 中写入 `allowed_qualities`，请求 key 时校验 scope + policy。

### 3.4 密钥管理与 token
- 密钥：`video_hls_encrypt_key` 记录 keyId、密文（KMS 加密）、IV、版本号、状态，可按 quality 绑定（quality 为空表示通用）。  
- token 发放：`IssueVideoHlsKeyTokenCmd` 生成短时 token（默认 10 分钟，可配置），落表 `video_hls_key_token`（哈希存储），携带 `allowed_qualities`。  
- 播放流程：
  1) 客户端调用 `POST /video/enc/token`（前台/后台）获取 token，返回授权的 qualities；  
  2) 客户端请求 master.m3u8（按授权过滤）；  
  3) 播放器在取 key 时携带 token（query），`GetEncryptedKeyPayload` 校验 token + scope → 解密 KMS 密钥 → 下发 16 字节明文；  
  4) token 默认一次性或有限使用次数（字段 `max_use`），可配置。
- 轮换：`RotateVideoHlsKeyCli` + `PersistVideoEncryptResultCmd` 写新 keyVersion，新 master/m3u8 下发时自动携带最新 keyId，旧 token 拒绝。

### 3.5 授权校验
- 权限校验点：`IssueToken`（校验用户身份/订单/会员 + 计算 `allowed_qualities`）+ `GetKey`（校验 token 未过期、未超次、未吊销 + 是否覆盖当前 quality）。  
- 管理端可跳过业务授权，但仍需 token 校验。  
- 请求追踪：`video_hls_key_token` 记录发放主体、过期时间、使用计数，便于审计。

### 3.6 兼容与回退
- 明文路径保持不变；`enc/` 独立，不影响现有播放。  
- 若 encrypt 失败或禁用，接口层回退到明文 m3u8。  
- 运维可通过命令重置 encrypt 状态、删除 enc 目录后重新加密（不破坏明文）。

## 四、数据库设计（见 `video_encrypt_decrypt_update.sql`）
1) `video_file_post`：新增加密状态/方式/密钥关联/失败原因。  
2) 新表 `video_hls_encrypt_key`：记录 keyId、密文、IV、keyVersion、状态、过期时间，可选绑定 quality。  
3) 新表 `video_hls_quality_auth`：存储分辨率授权策略（quality → auth_policy）。  
4) 新表 `video_hls_key_token`：记录发放的 token（哈希）、绑定 keyId/fileId、过期时间、最大使用次数/已使用次数、签发主体、状态、`allowed_qualities`。

## 五、DDD / 应用元素（见 `video_encrypt_decrypt_gen.json`）
- CLI（防腐层）：`GenerateVideoHlsKey`、`EncryptHlsWithKey`、`RotateVideoHlsKey`。  
- 命令：`PersistVideoEncryptResult`、`IssueVideoHlsKeyToken`、`ConsumeVideoHlsKeyToken`。  
- 查询：`GetVideoEncryptStatus`、`GetLatestVideoHlsKey`。  
- Payload（前台/后台）：获取加密 master / variant / segment、申请 token、取 key。

## 六、接口与契约（示例）
- 前台：
  - `POST /video/enc/token` `{ fileId }` → `{ token, expireAt }`
  - `GET /video/enc/videoResource/{fileId}/master.m3u8`（返回已替换 token 的内容）
  - `GET /video/enc/videoResource/{fileId}/{quality}/index.m3u8`
  - `GET /video/enc/videoResource/{fileId}/{quality}/{ts}`（enc 目录）
  - `GET /video/enc/key` `?keyId=&token=` → `binary aes-key`
- 后台：同路径增加 `/admin/video/enc/...`，token 由管理员身份签发。

## 七、交付物
- 设计文档：`iterate/video-encrypt-decrypt/video-encrypt-decrypt.md`
- DDD 设计 JSON：`iterate/video-encrypt-decrypt/video_encrypt_decrypt_gen.json`
- 数据库脚本：`iterate/video-encrypt-decrypt/video_encrypt_decrypt_update.sql`

## 八、非目标与后续
- 不覆盖端侧 DRM/防录屏；不提供下载/离线解密。  
- 不实现多租户 KMS 管理/密钥 escrow，当前默认单租户主密钥。  
- 后续可扩展：每清晰度独立 key、按用户/订单粒度 token、对接 CDN 鉴权。
