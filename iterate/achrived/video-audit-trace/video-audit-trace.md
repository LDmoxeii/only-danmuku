# 视频审核追溯需求说明

## 一、背景与目标

当前视频审核通过/不通过已有领域事件与消息通知，但缺少可追溯的审核记录（审核人、结果、原因、时间等）。为满足审计与运营追责需求，本迭代在视频审核流中植入日志钩子，落库存证，便于后续查询与分析。

## 二、范围

- 覆盖入口：视频审核指令（`AuditVideoPostCmd`）触发的审核结果：
  - 审核通过：`VideoAuditPassedDomainEvent`
  - 审核不通过：`VideoAuditFailedDomainEvent`
- 每次审核结果都写一条审核追溯记录。
- 不改现有审核决策逻辑，仅在事件订阅侧追加记录。

## 三、设计要点

### 3.1 审核记录模型

新表 `video_audit_trace`（见 SQL）核心字段：
- `video_id`：视频ID；
- `audit_status`：审核状态枚举（0:UNKNOW | 1:PASSED | 2:FAILED；@T=AuditStatus）；
- `reviewer_id`：审核人ID；
- `reviewer_type`：审核人类型（UserType，前台用户/系统管理员，缺省 UNKNOW）；
- `reason`：失败原因/备注；
- `occur_time`：审核发生时间；
- 审计字段：`create_user_id/create_by/create_time/update_user_id/update_by/update_time/deleted`（默认值遵循审计约束）。

### 3.2 领域/应用元素

- 复用领域事件：`VideoAuditPassedDomainEvent`、`VideoAuditFailedDomainEvent`（aggregate: `VideoPost`）。
- 新增命令（user_behavior 或 video_post.audit_trace 维度）：`RecordVideoAuditTraceCmd`，负责写入 `video_audit_trace`。
- 新增订阅者：监听上述两个领域事件，构造并发送 `RecordVideoAuditTraceCmd`。

### 3.3 流程钩子

- 审核通过链路：`CompatibleAdminVideoController.audit` → `AuditVideoPostCmd` → `VideoPost.audit` → `VideoAuditPassedDomainEvent` → `VideoAuditPassedDomainEventSubscriber` → `RecordVideoAuditTraceCmd(PASSED)`。
- 审核不通过链路：同上，事件为 `VideoAuditFailedDomainEvent`，命令入参 `audit_status=FAILED`，`reason` 按审核拒绝原因透传。

## 四、数据库设计

详见 `video_audit_trace_update.sql`：
- Drop & Create `video_audit_trace`（如已存在则重建）；
- 索引：`idx_video_audit_trace_video_time`（video_id, occur_time）、`idx_video_audit_trace_status_time`（audit_status, occur_time）。
- 枚举注释使用 `@E`、`@T` 以便代码生成。

## 五、DDD 设计元素

见 `video_audit_trace_gen.json`：
- `cmd`: `RecordVideoAuditTrace`（package: `video_post`），落审核记录。
- `de`: `VideoAuditPassed` / `VideoAuditFailed`（已有，聚合 `VideoPost`，此处仅标注复用）。

## 六、流程图

位于本目录：
- `VideoPost_audit_pass_trace.mmd`：审核通过记录链路。
-, `VideoPost_audit_fail_trace.mmd`：审核不通过记录链路。

## 七、边界与后续

- 本迭代不提供查询/报表接口；后续若需查询可新增 Query + 读模型。
- 审核人信息依赖当前登录管理员；若缺失则落空值并标注 UNKNOW。
