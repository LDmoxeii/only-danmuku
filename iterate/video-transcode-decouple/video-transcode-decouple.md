# 视频转码防腐层解耦迭代设计

## 背景与目标
当前 `TranscodeAllTranscodingFilesCmd` 直接在命令/聚合内操作转码流程，并侵入 `VideoFilePost.transcode`，违反“命令不依赖防腐层”的规约。目标：
- 转码业务下沉到防腐层 CLI（AC), 命令/聚合只处理状态持久化；
- 统一转码结果回写命令；`VideoPost` 状态变更独立命令；
- 控制器发布流程改用新链路。

## 范围
- 新增防腐 CLI：调用外部转码服务，返回转码结果（成功/失败、输出路径）。
- 新增命令：根据转码结果更新 `VideoFilePost`；执行整体状态推进（如全部完成→待审核/失败）。
- 控制器：`CompatibleUCenterVideoPostController.postVideo` 改为“上传→触发转码 CLI→回写命令”链路。
- 不改存量上传/持久化模型；不在命令里直接调用转码 SDK。

## 设计要点
1. 防腐 CLI
   - `TranscodeVideoFileCli`（requests：videoFilePostId、sourcePath、targetDir、profile...；response：success/failed、outputPath、duration、failReason）。
   - CLI Handler 负责调用转码引擎/脚本。命令层不直接依赖转码实现。
2. 状态回写命令
   - `UpdateVideoFilePostTranscodeResultCmd`：加载 `VideoFilePost`，调用 `applyTranscodeResult(...)` 仅更新状态/路径/时长。
   - 不直接操作文件；仅持久化。
3. 视频稿件状态命令
   - `RefreshVideoPostTranscodeStatusCmd`：检查同稿件下所有 `VideoFilePost` 状态（查询/仓储），决定 `VideoPost` 是否进入 `PENDING_REVIEW` 或 `REVIEW_FAILED`。
4. 控制器流程（postVideo）
   1) 上传信息持久化（沿用现有逻辑）；
   2) 每个文件：`CreateVideoFilePostCmd` → 发布 `VideoFilePostCreatedDomainEvent`；
   3) 订阅者收到事件后调用防腐 `TranscodeVideoFileCli`；
   4) 删除文件：`DeleteVideoFilePostCmd` → 发布 `VideoFilePostDeletedDomainEvent`；
   5) CLI 结果驱动 `UpdateVideoFilePostTranscodeResultCmd`，并发布 `VideoFilePostTranscodeResultUpdatedDomainEvent`；
   6) 订阅删除/回写事件，调用 `RefreshVideoPostTranscodeStatusCmd` 推进稿件状态。
5. 领域职责
   - `VideoFilePost` 聚合只提供“应用转码结果”的领域方法，不调用外部服务。
   - `VideoPost` 聚合提供“刷新稿件转码完成度”的领域方法（或由命令协调）。

## 交付物
- 需求/设计：`iterate/video-transcode-decouple/video-transcode-decouple.md`
- DDD 设计 JSON：`iterate/video-transcode-decouple/video_transcode_decouple_gen.json`
  - CLI：`TranscodeVideoFile`
  - 命令：`CreateVideoFilePost`、`UpdateVideoFilePostTranscodeResult`、`RefreshVideoPostTranscodeStatus`
  - 查询：`GetVideoFilePostsByPostId`
- 流程图：
  - `Transcode_video_file_flow.mmd`：控制器 → CreateVideoPostCmd → (loop 文件) CreateVideoFilePostCmd → TranscodeVideoFileCli → UpdateVideoFilePostTranscodeResultCmd → RefreshVideoPostTranscodeStatusCmd。
  - `Refresh_video_post_status_flow.mmd`：回写后 → 查询稿件文件 → 刷新 `VideoPost` 状态。

## 非目标 / 后续
- 不改转码实现细节（编码参数/模板）。若需 HLS 加密另行迭代。
- 不覆盖异步调度；可后续扩展队列/重试。
