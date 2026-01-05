# OSS 存储引入设计说明

## 0. 问题清单（单 key 全量加密）
- [x] `RotateVideoHlsKeyCmd` 仅轮换“全局 key”（`quality = null`），新 key 也固定 `quality = null`，确保触发全量加密。
- [ ] `GetLatestVideoHlsKeyQry` / token 签发逻辑应只使用全局 key（忽略带 `quality` 的历史数据），避免误取。
- [ ] 单 key 模式下“清晰度分级授权”无法做到密钥隔离，需确认业务接受该限制或追加接口层强校验策略。
- [x] `computeAllowedQualities()` 为空会导致 token 放行全部质量，需决定是否改为“无可用质量直接拒发 token”。
- [x] 轮换时旧 key 是否标记 `REVOKED`（避免旧 token/旧 key 被继续使用）。
- [x] 全量加密覆盖 OSS 前缀时，是否需要先删除旧对象，避免残留文件。

## 0.1 未来扩展：按清晰度独立 key（当前不做）
- [ ] Token 当前绑定单个 `keyId/keyVersion`（`video_hls_key_token`），按清晰度独立 key 需要：每清晰度签发 token，或解耦 token 与 keyId 并在取 key 时校验 keyId 属于同一 fileId。
- [ ] `IssueVideoHlsKeyTokenCmd` + `GetLatestVideoHlsKeyQry` 假设只有一个 key，需要调整为支持按清晰度取 key 或发放通用 token。
- [ ] `VideoFilePostTranscodeResultUpdatedDomainEventSubscriber` 仅生成一个 key（quality=null），需改为按 ABR 清晰度生成多个 key。
- [ ] `VideoHlsEncryptKeyCreatedDomainEventSubscriber` 会对每个 key 触发加密，可能并发写同一 `enc/` 前缀；需要串行化或加锁避免覆盖/竞态。
- [ ] `ConsumeVideoHlsKeyTokenCmd` 未验证请求的 `quality` 与 key 绑定的 `quality` 一致，按清晰度独立 key 需要补充校验。
- [ ] `GetVideoEncryptStatusQry` 依赖 `video_file_post.encrypt_key_id`（单值），独立 key 后语义不明确，需要调整或弱化依赖。
- [ ] `RotateVideoHlsKeyCmd` 只轮换最新 key（跨质量），应支持按清晰度轮换或批量轮换。
- [ ] `computeAllowedQualities()` 为空时返回 null（等同放行所有质量），需要明确“无可用质量”是否应拒绝发 token。

## 一、背景与目标
- 现有文件资源均落本地磁盘（`app.file.projectFolder + file/`），容量与 IO 成本高，扩容依赖机器。
- 目标：引入 OSS 对象存储，覆盖**图片资源**与**视频转码产物**的持久化与访问，降低本地磁盘依赖，并为 CDN 加速预留空间。
- 迁移策略：不考虑兼容模式，**全量迁移到 OSS**，本地仅保留临时文件。

## 二、范围
1) 图片资源：上传图片（封面、头像、分类图标等）、缩略图与通用文件资源读取。  
2) 视频转码产物：ABR master.m3u8、分辨率 playlist、ts 分片；以及加密 HLS enc/ 目录。  
3) 临时文件：转码过程中产生的**临时 MP4**统一切换到系统临时目录（`Files.createTempFile`）。  
不在本迭代：前端直传 OSS、DRM、下载离线。

## 三、现状盘点（可 OSS 化部分）
### 3.1 图片/通用文件资源
- 上传入口：`CompatibleFileController#uploadImage`、`CompatibleAdminFileController#uploadImage` → `UploadImageCli`。  
- 落盘位置：`UploadImageCliHandler` 保存到 `file/cover/{yyyyMM}/`，生成缩略图。  
- 访问入口：`/file/getResource`、`/admin/file/getResource` → `GetFileResourceQryHandler` 读取 `file/` 下路径。  
- 存储字段（相对路径）：  
  - `video.video_cover` / `video_post.video_cover`  
  - `customer_profile.avatar`  
  - `category.icon`

### 3.2 视频上传与转码
- 上传分片：`UploadVideoChunkCmd` 写入 `file/temp/{yyyyMMdd}/{userId}/{uploadId}`。  
- 合并临时 MP4：`MergeUploadToMp4CliHandler` 输出 `file/video/{customerId}/{videoId}/{fileIndex}/temp.mp4`。  
- ABR 转码输出：`TranscodeVideoFileToAbrCliHandler` 生成 `master.m3u8` + `{quality}/index.m3u8` + `*.ts`。  
- 访问入口：`VideoAbrController` 直接读取本地 m3u8/ts。  
- 清理入口：`CleanupTempUploadDirCli` / `CleanupMergedMp4Cli` / `DeleteVideoFileResourcesCli`（删除本地资源）。

### 3.3 加密 HLS（enc）
- `EncryptHlsWithKeyCliHandler` 生成 `enc/` 目录（`master.m3u8` + 变体 + ts.enc）。  
- `VideoEncryptController` 读取本地 enc 目录并替换 token。  

## 四、影响面清单
### 4.1 接口（API）
- `/file/getResource`、`/file/uploadImage`（`CompatibleFileController`）  
- `/file/preUploadVideo`、`/file/uploadVideo`、`/file/delUploadVideo`（`CompatibleFileController`）  
- `/admin/file/getResource`、`/admin/file/uploadImage`（`CompatibleAdminFileController`）  
- `/video/abr/videoResource/{fileId}/master.m3u8`（`VideoAbrController`）  
- `/video/abr/variants`（`VideoAbrController`）  
- `/video/abr/videoResource/{fileId}/{quality}/index.m3u8`（`VideoAbrController`）  
- `/video/abr/videoResource/{fileId}/{quality}/{ts}`（`VideoAbrController`）  
- `/video/enc/token`、`/video/enc/qualities`（`VideoEncryptController`）  
- `/video/enc/videoResource/{fileId}/master.m3u8`（`VideoEncryptController`）  
- `/video/enc/videoResource/{fileId}/{quality}/index.m3u8`（`VideoEncryptController`）  
- `/video/enc/videoResource/{fileId}/{quality}/{ts}`（`VideoEncryptController`）  
- `/video/enc/key`（`VideoEncryptController`）  
- `/admin/video/abr/videoResource/{fileId}/master.m3u8`（`VideoAbrAdminController`）  
- `/admin/video/abr/variants`（`VideoAbrAdminController`）  
- `/admin/video/abr/videoResource/{fileId}/{quality}/index.m3u8`（`VideoAbrAdminController`）  
- `/admin/video/abr/videoResource/{fileId}/{quality}/{ts}`（`VideoAbrAdminController`）  
- `/admin/video/enc/videoResource/{fileId}/master.m3u8`（`VideoEncryptAdminController`）  
- `/admin/video/enc/videoResource/{fileId}/{quality}/index.m3u8`（`VideoEncryptAdminController`）  
- `/admin/video/enc/videoResource/{fileId}/{quality}/{ts}`（`VideoEncryptAdminController`）  
- `/admin/video/enc/key`（`VideoEncryptAdminController`）  

### 4.2 命令（Cmd）
- `CreateUploadSessionCmd`（创建上传会话）  
- `InitTempAndStartUploadingCmd`（初始化临时目录）  
- `UploadVideoChunkCmd`（写入分片）  
- `DeleteUploadSessionCmd`（清理临时目录）  
- `UpdateVideoFilePostTranscodeResultCmd`（落库 `file_path` 与 ABR 结果）  
- `PersistVideoEncryptResultCmd`（落库加密目录 `encryptedMasterPath`）  

### 4.3 查询（Qry）
- `GetFileResourceQry`（图片/通用资源）  
- `GetVideoResourceQry` / `GetVideoResourceTsQry`（视频资源）  
- `GetVideoPostResourceQry` / `GetVideoPostResourceTsQry`（稿件态资源）  
- `GetVideoAbrMasterQry` / `ListVideoAbrVariantsQry`（ABR 资源路径）  
- `GetVideoPostIdByFileIdQry`（文件与稿件映射）  
- `GetVideoEncryptStatusQry`（加密目录路径）  

### 4.4 适配层（Cli）
- `UploadImageCli`（图片写入）  
- `MergeUploadToMp4Cli`（合并分片为临时 MP4）  
- `TranscodeVideoFileToAbrCli`（ABR 转码输出）  
- `CleanupMergedMp4Cli`（删除临时 MP4）  
- `CleanupTempUploadDirCli` / `CleanTempFilesCli`（清理临时目录）  
- `DeleteVideoFileResourcesCli`（删除视频资源前缀）  
- `EncryptHlsWithKeyCli`（加密 HLS 输出）  

### 4.5 其他影响
- 配置：`only.engine.oss.*`、`app.file.projectFolder`（仅保留临时路径用途）。  
- 常量：`Constants.FILE_FOLDER`、`FILE_VIDEO`、`FILE_COVER`、`FILE_FOLDER_TEMP`、`TEMP_VIDEO_NAME`。  
- 资源字段（存 objectName 或基于 objectName 推导）：`video.file_path`、`video_file_post.file_path`、`video.video_cover`、`video_post.video_cover`、`customer_profile.avatar`、`category.icon`、`video_file_upload_session.temp_path`。  

## 五、目标设计
### 5.1 存储策略
- 使用 `engine-oss`（`OssFactory` + `OssClient`）作为统一对象存储适配层。  
- 资源 key **沿用当前相对路径**（如 `cover/202501/xxx.png`、`video/{uid}/{vid}/{idx}/master.m3u8`），避免 DB 扩展长度问题。  
- 访问层统一输出 OSS URL 或执行 OSS 代理下载，不再读取本地文件。

### 5.2 图片上传流程（OSS）
1) 接收 MultipartFile → 本地临时文件（必要时）。  
2) 生成缩略图（可选）→ 上传原图/缩略图至 OSS。  
3) 返回资源 key（兼容现有返回值），并在需要时返回可访问 URL。  
4) 清理本地临时文件。

### 5.3 视频转码与 OSS 上传
1) 分片上传仍保留本地临时目录。  
2) 合并 MP4 产物改为系统临时目录（`Files.createTempFile`），路径改为绝对值。  
3) ABR 输出仍在本地生成（FFmpeg 需要本地目录），完成后上传 `outputDir` 到 OSS（含 master/variants/segments）。  
4) `enc/` 目录（如启用加密）同样上传到 OSS。  
5) 更新 `video_file_post.file_path`（仍为相对前缀），后续资源访问走 OSS URL。

### 5.4 资源访问
- 图片：`/file/getResource` 返回 OSS URL（302/JSON）或代理读取 OSS。  
- ABR：`VideoAbrController` 读取 OSS 中的 master/playlist/segment（优先返回 URL 或重定向）。  
- 加密 HLS：`VideoEncryptController` 读取 OSS 中的 enc m3u8 后替换 token；ts.enc 可直接指向 OSS。  
- 不保留本地兜底读取。

### 5.5 清理与删除
- `DeleteVideoFileResourcesCli` 删除 OSS 前缀（`video/{uid}/{vid}/{idx}/`）。  
- `CleanupTempUploadDirCli` / `CleanupMergedMp4Cli` 允许接受绝对临时路径并清理。

### 5.6 临时文件改造（强制）
- 合并临时 MP4 必须使用 `Files.createTempFile` 创建（系统临时目录）。  
- 相关清理任务必须支持绝对路径并保证安全删除。  

## 六、配置建议
- OSS 配置：`only.engine.oss.*`（已有）。  
- URL 生成：由 OSS domain/presign 生成。

## 七、全量迁移方案
1) 资源盘点：按 `cover/`、`video/`、`video/.../enc/` 三类统计本地存量与大小。  
2) 批量迁移：离线脚本将本地 `file/` 下存量上传至 OSS（保持 key 不变）。  
3) 校验：抽样比对对象数量、大小、ETag（或 hash）。  
4) 切换：接口访问与上传统一走 OSS。  
5) 清理：确认稳定后删除本地 `file/` 存量，仅保留临时目录。

## 八、数据库设计
本迭代仅需要支持临时路径为**绝对路径**的长度扩展：  
- `video_file_upload_session.temp_path` 建议扩展至 `varchar(512)`。  
其他字段（`file_path`、`video_cover`、`avatar`、`icon`）继续存**相对 key**，不需要变更。

## 九、交付物
- 设计文档：`iterate/oss-storage/oss-storage.md`  
- DDD 设计 JSON：`iterate/oss-storage/oss_storage_gen.json`  
- 数据库脚本：`iterate/oss-storage/oss_storage_update.sql`
