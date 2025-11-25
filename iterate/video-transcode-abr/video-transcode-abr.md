# 视频多分辨率 + 自适应码率（ABR）转码需求说明

## 一、背景与目标
- 现有上传/转码流程仅产出单一清晰度，弱网络下体验不佳，且部分终端需要标准 HLS ABR 输出。
- 本迭代引入“动态分辨率决策 + 多路 HLS 切片 + Master Playlist”，让前端播放器自动选择合适码率，同时保证低分辨率回退。
- 约束：源视频决定最高档位；对外仍以 HTTP 形式暴露 `master.m3u8`/子清晰度索引和 ts 片段。

## 二、本次迭代范围
1. 分辨率探测：上传后通过 FFprobe 读取首路视频的宽高，示例命令：
   - `ffprobe -v error -select_streams v:0 -show_entries stream=width,height -of csv=s=x:p=0 input.mp4`
2. 档位选择策略：预置档位 `1080p/720p/480p/360p`（宽×高、视频/音频码率见第 3.1 节），只生成不高于源分辨率的档位。
3. 多路转码：使用 FFmpeg 一次生成多清晰度 HLS（ts/m3u8），并写出 `master.m3u8`。
4. 输出与存储：落地目录规范 `/videos/{fileId}/master.m3u8`、`/videos/{fileId}/{quality}/index.m3u8` 与 ts 片段。
5. 元数据：`video_file_post` 增加 ABR 元数据字段（源分辨率/输出目录/master 路径），`video_hls_abr_variant` 记录每个档位；`video_file` 表保持干净。
6. 触发与回写：基础转码取消，稿件发布后直接调用 ProbeVideoResolutionCli → TranscodeVideoFileToAbr（替换 `TranscodeVideoFileCliHandler`），并在 `UpdateVideoFilePostTranscodeResultCmd` 内写入 ABR 元数据与档位；`TranscodeVideoFileCli` 与旧 Handler 标记弃用。
7. 对外接口（静态/网关）：提供
   - `/videoResource/{fileId}/master.m3u8`
   - `/videoResource/{fileId}/{quality}/index.m3u8`
   - `/videoResource/{fileId}/{quality}/{ts}`

## 三、流程与策略
### 3.1 档位配置（默认值，可通过配置中心调整）
- `1080p: 1920x1080, video 5000k, audio 128k`
- `720p: 1280x720,  video 2800k, audio 128k`
- `480p: 854x480,   video 1400k, audio 96k`
- `360p: 640x360,   video 800k,  audio 64k`
- 生成规则：若 `SRC_HEIGHT < 档位高度` 则跳过该档位。
- Master 带宽字段：`(video_k + audio_k) * 1000`（bps），用于 `#EXT-X-STREAM-INF`。

### 3.2 转码链路（单个 fileId）
```
稿件发布 → 触发 ABR 转码
        ↓
Probe 源分辨率（ffprobe）
        ↓
根据可用档位构建转码任务参数
        ↓
FFmpeg 多路转码 + HLS 切片（6s 片段，vod 模式）
        ↓
生成 {quality}/index.m3u8 与 ts 片段
        ↓
生成 master.m3u8（注册可用档位与带宽）
        ↓
写入存储（本地或对象存储），记录元数据
        ↓
UpdateVideoFilePostTranscodeResultCmd 回写 transfer_result 与 ABR 元数据/variant
```

### 3.3 目录/路径规范
```
/video/{customerId}/{videoId}/{fileIndex}/
├── master.m3u8
├── 1080p/
│   ├── index.m3u8
│   ├── 0000.ts ...
├── 720p/
│   ├── index.m3u8
│   ├── 0000.ts ...
├── 480p/
│   ├── index.m3u8
│   ├── 0000.ts ...
└── 360p/
    ├── index.m3u8
    ├── 0000.ts ...
```
- 相对路径前缀来自上传合并输出 `video/{customerId}/{videoId}/{fileIndex}`，其中 `fileIndex` 目录下再分档位和 master。
- 前台取资源仍按 `fileId`（`video_file`）→ 反查 `video_file_post_id` → 按上述相对路径拼接。

## 四、数据库设计（见 `video_transcode_abr_update.sql`）
1. 扩展 `video_file_post`
   - 字段：`abr_source_width/height/bitrate_kbps`；状态沿用 `transfer_result`（即该转码结果代表 ABR 多路转码结果）。master.m3u8 路径可由 `file_path` 派生（同目录）。
2. 新表 `video_hls_abr_variant`
   - 关联：`file_id`（稿件态 fileId，必要）。
   - 字段：`quality`（1080p/720p/...）、宽高、视频/音频码率、带宽、variant m3u8 路径、切片前缀、segment 时长。
   - 索引：`uk_video_hls_abr_variant`（`file_id + quality + deleted` 唯一）、`idx_video_hls_abr_variant_height`。
- 前台访问：通过 `video_file.id` 反查 `video_file_post_id`，再取 `video_file_post` + `video_hls_abr_variant`；后台直接用 `video_file_post_id`。
- 说明：`transfer_result` 继续表示基础转码（单路）结果；ABR 的独立状态用 `abr_status`，避免干扰现有“转码完成→进入审核”逻辑。

## 五、DDD/应用设计（见 `video_transcode_abr_gen.json`）
- 触发链路：在稿件发布后触发 ABR（不再执行基础单路转码）；订阅器/流程直接调用：
  - ProbeVideoResolutionCli（输入 fileId/path → 输出分辨率）；
  - TranscodeVideoFileToAbrCli（输入 fileId/path/档位 → 输出 master/variant 路径与 variants 列表，替代旧 TranscodeVideoFile CLI）；
  - 成功后调用 `UpdateVideoFilePostTranscodeResultCmd` 更新 `transfer_result` 为成功/失败，并写入 ABR 元数据与 variant 表。
- CLI
  - `ProbeVideoResolutionCli`：防腐层获取源分辨率（含稿件态 fileId 与上线后 fileId）。
  - `TranscodeVideoFileToAbrCli`：调用 FFmpeg/脚本生成多清晰度 HLS 与 master，响应返回 `accepted`、`masterPlaylistPath`、`variants`（quality/宽高/码率/playlist/segmentPrefix/bandwidth/segmentDuration）。
- 命令
  - `UpdateVideoFilePostTranscodeResultCmd`：扩展写入 ABR 元数据与 variant（替代单路转码结果回写）。
- 领域事件：本迭代不新增 ABR 专用事件。
- 查询
  - `GetVideoAbrMasterQry`：查询 master.m3u8 路径与输出状态（支持 post/file 双 ID）。
  - `ListVideoAbrVariantsQry`：查询可用清晰度档位列表。

## 六、接口与对外契约
- 前台（发布态 fileId，POST）：
  - `POST /api/video/abr/master`：入参 `fileId`，返回 master.m3u8 路径（自动播放用）。
  - `POST /api/video/abr/variants`：入参 `fileId`，返回可选档位列表（quality、宽高、码率、路径）。
  - `POST /api/video/abr/playlist`：入参 `fileId`、`quality`，返回该档位 m3u8 路径。
  - `POST /api/video/abr/segment`：入参 `fileId`、`quality`、`ts`，返回 ts 片段路径。
- 后台（稿件态 filePostId，POST，与前台同形）：
  - `POST /api/admin/video/abr/master`：`filePostId` → master 路径。
  - `POST /api/admin/video/abr/variants`：`filePostId` → 档位列表。
  - `POST /api/admin/video/abr/playlist`：`filePostId`、`quality` → m3u8 路径。
  - `POST /api/admin/video/abr/segment`：`filePostId`、`quality`、`ts` → ts 路径。
- 自动播放：客户端选择“自动”时走 master（ABR）；手动指定档位时走 playlist/segment 接口。
- 运维重触发转码本迭代不做。

## 七、交付物
- 需求/设计文档：`iterate/video-transcode-abr/video-transcode-abr.md`
- SQL：`iterate/video-transcode-abr/video_transcode_abr_update.sql`
- DDD 设计 JSON：`iterate/video-transcode-abr/video_transcode_abr_gen.json`

## 八、非目标与后续
- 不实现动态码率自适应（基于实时带宽反馈）以外的自定义档位配置 UI，档位改动通过配置中心或脚本调整。
- 不改存量加密/HLS 防盗链逻辑；与 HLS AES-128 兼容的 key 复用在后续迭代讨论。
- 不提供播放器 SDK 改造；仅确保输出符合标准 HLS。
