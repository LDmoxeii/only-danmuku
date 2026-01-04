# 视频审核通过流程

## 流程描述
- 管理员调用 `/admin/videoInfo/auditVideo`，状态为 `REVIEW_PASSED`。
- 控制器将状态映射为 `AuditStatus.PASSED`，执行 `RecordVideoAuditTraceCmd`。
- `RecordVideoAuditTraceCmd` 通过 `Mediator.factories.create` 创建 `VideoAuditTrace`，自动触发 `onCreate` 并发布 `VideoAuditTraceRecordedDomainEvent`，随后保存。
- `VideoAuditTraceRecordedDomainEventSubscriber` 触发两条路径：
  - 调用 `AuditVideoPostCmd` 将稿件标记为审核通过（`reviewPass`），产生 `VideoAuditPassedDomainEvent`。
  - 调用 `SendVideoAuditPassedMessageCmd` 发送审核通过系统消息。
- `VideoAuditPassedDomainEventSubscriber` 处理三条联动：
  - `RewardUserForVideoCmd` 奖励用户发布视频硬币。
  - `TransferVideoToProductionCmd` 将稿件同步为成品视频；若成品视频不存在则通过 `Mediator.factories.create` 新建并触发 `onCreate`。
  - 查询上传临时目录并调用 `CleanTempFilesCli` 清理临时文件。

## 时序图
```mermaidsequenceDiagram
  autonumber
  actor Admin as Admin
  participant Ctrl as CompatibleAdminVideoController
  participant RecordCmd as RecordVideoAuditTraceCmd
  participant Trace as VideoAuditTrace
  participant TraceSub as VideoAuditTraceRecordedDomainEventSubscriber
  participant AuditCmd as AuditVideoPostCmd
  participant Post as VideoPost
  participant MsgCmd as SendVideoAuditPassedMessageCmd
  participant PassSub as VideoAuditPassedDomainEventSubscriber
  participant RewardCmd as RewardUserForVideoCmd
  participant TransferCmd as TransferVideoToProductionCmd
  participant Video as Video
  participant TempQry as GetUploadedTempPathsQry
  participant CleanCli as CleanTempFilesCli

  Admin->>Ctrl: POST /admin/videoInfo/auditVideo(videoId, status=REVIEW_PASSED)
  Ctrl->>RecordCmd: RecordVideoAuditTraceCmd.Request(videoPostId, auditStatus=PASSED, reviewer...)
  RecordCmd->>Trace: Mediator.factories.create(VideoAuditTraceFactory.Payload)
  Note over Trace: onCreate auto-triggered by Mediator.factories.create
  Trace-->>RecordCmd: VideoAuditTraceRecordedDomainEvent
  RecordCmd->>RecordCmd: UoW save

  Trace-->>TraceSub: VideoAuditTraceRecordedDomainEvent
  TraceSub->>AuditCmd: AuditVideoPostCmd.Request(status=REVIEW_PASSED, reason)
  TraceSub->>MsgCmd: SendVideoAuditPassedMessageCmd.Request(videoId, operatorId)

  AuditCmd->>Post: reviewPass() -> VideoAuditPassedDomainEvent
  AuditCmd->>AuditCmd: UoW save
  Post-->>PassSub: VideoAuditPassedDomainEvent

  Note over PassSub: three listeners (reward, transfer, cleanup)
  PassSub->>RewardCmd: RewardUserForVideoCmd.Request(customerId)
  PassSub->>TransferCmd: TransferVideoToProductionCmd.Request(...)
  TransferCmd->>TransferCmd: load VideoFilePosts + find existing Video
  alt video exists
    TransferCmd->>Video: syncFromBasics(...)
  else new video
    TransferCmd->>Video: Mediator.factories.create(VideoFactory.Payload)
    Note over Video: onCreate auto-triggered by Mediator.factories.create
  end
  TransferCmd->>TransferCmd: UoW save
  PassSub->>TempQry: GetUploadedTempPathsQry(customerId, videoId)
  TempQry-->>PassSub: tempPaths
  PassSub->>CleanCli: CleanTempFilesCli.Request(tempPaths)
```
