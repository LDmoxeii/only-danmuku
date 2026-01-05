# 用户行为监控（登录日志与异常操作）需求说明

## 一、背景与目标

当前系统已经支持：
- 密码登录与短信验证码登录（`/account/login`、`/account/loginBySms`）；
- 账号安全能力（修改密码、手机号绑定等）；
- 基于验证码引擎的短信验证码发送与校验。

但对“用户登录行为”尚未进行结构化记录，也没有对连续密码错误的异常行为进行专门监控。这会导致：
- 难以追踪登录来源、频率与失败原因，影响安全运营与问题排查；
- 无法对高频密码错误行为进行集中分析与风控（例如暴力破解、撞库等）。

本次迭代目标：
1. 在登录流程中植入统一的“登录日志”记录钩子；
2. 当同一账号在一定时间窗口内密码输入错误累计达到 5 次时，记录一条“异常操作日志”，为后续风控策略提供依据。

## 二、本次迭代范围

### 2.1 必做项

1. 登录日志记录
   - 覆盖以下登录/登出入口：
     - 前台用户：`/account/login`（邮箱+密码登录）、`/account/loginBySms`（手机号+短信验证码登录）、`/account/logout`（退出登录）；
     - 管理员（后台）：`/admin/account/login`（账号+密码登录）、管理端统一登出接口（调用 `StpUtil.logout` 的入口）。
   - 每次登录尝试（成功/失败）以及主动登出操作，均需落一条登录日志，至少包含：
     - 登录账号标识（邮箱或手机号）；
     - 用户类型（UserType：前台用户 / 系统管理员等）；
     - 登录类型（LoginType：PASSWORD / SMS_CODE / LOGOUT 等）；
     - 登录结果（LoginResult：SUCCESS / FAILURE / UNKNOW 等）；
     - 登录 IP；
     - 发生时间。

2. 密码输入错误次数监控
   - 对密码登录（`/account/login`）场景，在密码校验失败时记录失败登录日志；
   - 当同一账号在一定时间窗口内（例如最近 30 分钟）密码输入错误次数累计达到 5 次时：
     - 记录一条“异常操作日志”，类型为“PASSWORD_FAIL_TOO_MANY_TIMES”；
     - 异常日志至少包含：账号标识、IP、触发次数、触发时间、可选的关联登录日志 ID 列表。

### 2.2 后续/可选项（本迭代不实现）

1. 异常操作触发后，自动锁定账号或强制校验额外因子（如短信验证码）；
2. 登录日志与异常事件的管理端查询、导出与统计报表；
3. 基于登录日志的地理位置/IP 黑名单规则。

## 三、模型与钩子设计

### 3.1 登录日志模型

新建登录日志表 `user_login_log`（见第 4 章 SQL）：
- `id`：主键；
- `user_id`：关联用户 ID（若登录失败且无法识别用户，可为空）；
- `login_name`：用户输入的登录名（邮箱或手机号）；
- `login_type`：登录类型（0: UNKNOWN, 1: PASSWORD, 2: SMS_CODE）；
- `result`：登录结果（0: FAILURE, 1: SUCCESS）；
- `ip`：登录 IP；
- `user_agent`：可选，客户端 UA 信息；
- `reason`：失败原因说明（如“密码错误”“验证码错误”等）；
- `occur_time`：发生时间。

### 3.2 异常操作日志模型

新建异常操作日志表 `user_abnormal_operation_log`：
- `id`：主键；
- `user_id`：关联用户 ID；
- `op_type`：异常类型（如 PASSWORD_FAIL_TOO_MANY_TIMES）；
- `ip`：触发时的 IP；
- `occur_time`：触发时间；
- `description`：异常描述（例如“30 分钟内密码连续错误 5 次”）；
- `extra`：可选扩展 JSON，存储关联登录日志 ID 列表、最近错误时间等。

### 3.3 钩子植入位置

1. 密码登录钩子（`/account/login`）
   - 当前流程：图形验证码校验 → `GetAccountInfoByEmailQry` → 密码校验 → `UpdateLoginInfoCmd` → 登录态写入；
   - 新增行为：
     - 在密码校验前后，统一调用“记录登录日志命令”：
       - 登录成功：记录 `result=SUCCESS` 的日志；
       - 登录失败（密码错误）：记录 `result=FAILURE` 的日志，并触发“密码输入失败”领域/应用事件。

2. 短信登录钩子（`/account/loginBySms`）
   - 在短信验证码校验 + `GetUserByPhoneQry` 完成后：
     - 登录成功：记录 `result=SUCCESS` + `login_type=SMS_CODE` 的登录日志；
     - 登录失败（短信验证码错误）：记录 `result=FAILURE` 的登录日志。

3. 异常操作统计钩子
   - 当发生一次密码错误（登录失败）时：
     - 通过事件（例如 `PasswordInputFailedEvent`）通知“异常操作统计”组件；
     - 组件内部基于登录日志表或 Redis 缓存计算最近时间窗口内的失败次数；
     - 当达到阈值 5 次时，写入一条 `user_abnormal_operation_log` 记录。

## 四、数据库变更设计

### 4.1 新增表：`user_login_log`

见 `Iterate/user-behavior-monitoring/user_behavior_monitoring_update.sql`：

- 主要字段：
  - `id` bigint PK；
  - `user_id` bigint NULL 默认 NULL；
  - `login_name` varchar(150) NOT NULL；
  - `login_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0:UNKNOWN|1:PASSWORD|2:SMS_CODE'；
  - `result` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0:FAILURE|1:SUCCESS'；
  - `ip` varchar(45) NOT NULL；
  - `user_agent` varchar(255) NULL；
  - `reason` varchar(200) NULL；
  - `occur_time` bigint NOT NULL；
  - `deleted` bigint NOT NULL DEFAULT 0。
- 索引：
  - 基于 `user_id`、`login_name`、`occur_time` 的查询索引，用于统计近 N 次失败；
  - `result` 字段可用于快速过滤成功/失败记录。

### 4.2 新增表：`user_abnormal_operation_log`

- 主要字段：
  - `id` bigint PK；
  - `user_id` bigint NOT NULL；
  - `op_type` varchar(50) NOT NULL COMMENT '异常类型，如 PASSWORD_FAIL_TOO_MANY_TIMES'；
  - `ip` varchar(45) NOT NULL；
  - `occur_time` bigint NOT NULL；
  - `description` varchar(200) NULL；
  - `extra` text NULL COMMENT '扩展 JSON，如关联 login_log_ids 等'；
  - `deleted` bigint NOT NULL DEFAULT 0。
- 索引：
  - 基于 `user_id` 和 `op_type` 的查询索引，方便按用户和类型查询异常记录。

## 五、DDD 设计要点（_gen.json 设计元素）

新建设计文件：`Iterate/user-behavior-monitoring/user_behavior_monitoring_gen.json`，核心元素包括：

1. 命令层（application 层）
   - `RecordLoginLogCmd`（package: `user_behavior`）
     - 负责写入 `user_login_log` 表；
     - 入参包含：`userId?`, `loginName`, `loginType`, `result`, `ip`, `reason?`, `occurTime` 等；
     - 由 Controller 或事件订阅者调用。
   - `RecordAbnormalOperationCmd`（package: `user_behavior`）
     - 负责写入 `user_abnormal_operation_log` 表；
     - 入参包含：`userId`, `opType`, `ip`, `description`, `extra?`, `occurTime` 等。

2. 领域事件（domain 层）
   - `PasswordInputFailedDomainEvent`（aggregate: `User`）
     - 在密码登录失败且能识别用户时触发；
     - 由登录流程或领域方法在检测到密码错误时发布（设计上可以通过重构 `User.isPasswordCorrect` + 新的领域方法实现）。

3. 订阅者（application/subscribers）
   - `PasswordInputFailedDomainEventSubscriber`
     - 监听 `PasswordInputFailedDomainEvent`；
     - 内部职责：
       1. 记录一次失败登录日志（调用 `RecordLoginLogCmd`，`result=FAILURE`）；
       2. 统计最近时间窗口内失败次数（可通过查询 `user_login_log` 或 Redis 缓存实现，具体实现细节可在后续迭代中细化）；
       3. 当达到 5 次时，调用 `RecordAbnormalOperationCmd` 写入异常操作日志。

4. 查询设计（queries）
   - `GetRecentPasswordFailureCountQry`（package: `user_behavior`）
     - 根据 `userId` 或 `loginName` + 时间窗口，统计登录失败次数；
     - 主要供 `PasswordInputFailedDomainEventSubscriber` 使用。

> 以上命令/事件/查询的具体字段与包名将在 `user_behavior_monitoring_gen.json` 中以设计元素方式定义，为后续 `./gradlew genDesign` 提供输入。

## 六、流程分析（Mermaid 流程图）

本迭代在 `Iterate/user-behavior-monitoring` 目录下提供以下流程图：

1. `CompatibleAccountController_login_log.mmd`
   - 描述前台密码登录成功/失败时的日志记录钩子：
     - 成功：`CompatibleAccountController.login` → `RecordLoginLogCmd(SUCCESS)`；
     - 失败（密码错误）：`CompatibleAccountController.login`（命令入口）→ `User.verifyPassword`（聚合领域方法）→ `PasswordInputFailedDomainEvent` → `PasswordInputFailedDomainEventSubscriber` → `RecordLoginLogCmd(FAILURE)` → `GetRecentPasswordFailureCountQry` → 当次数 ≥5 时 → `RecordAbnormalOperationCmd(PASSWORD_FAIL_TOO_MANY_TIMES)`。

2. `CompatibleAdminAccountController_login_log.mmd`
   - 描述后台管理员登录成功/失败时的日志记录钩子：
     - 成功：`CompatibleAdminAccountController.adminAccountLogin` → `RecordLoginLogCmd(SUCCESS)`；
     - 失败（密码错误）：`CompatibleAdminAccountController.adminAccountLogin`（命令入口）→ `User.verifyPassword`（聚合领域方法）→ `PasswordInputFailedDomainEvent` → `PasswordInputFailedDomainEventSubscriber` → `RecordLoginLogCmd(FAILURE)` → `GetRecentPasswordFailureCountQry` → 当次数 ≥5 时 → `RecordAbnormalOperationCmd(PASSWORD_FAIL_TOO_MANY_TIMES)`。

3. `CompatibleAccountController_logout_log.mmd`
   - 描述前台用户退出登录时的日志记录链路：
     - `CompatibleAccountController.logout` → `RecordLoginLogCmd(LOGOUT)`。

4. `CompatibleAdminAccountController_logout_log.mmd`
   - 描述管理员退出登录时的日志记录链路（管理端登出入口）：
     - `CompatibleAdminAccountController.logout`（或等价登出入口）→ `RecordLoginLogCmd(LOGOUT)`。

## 七、非功能与约束

- 性能与存储：
  - 登录日志可能数据量较大，应在设计上考虑归档策略与按时间分区（可在后续迭代实现）；
  - 统计失败次数时优先基于时间窗口避免全表扫描。

- 安全与隐私：
  - 登录日志中不记录明文密码，仅记录必要的标识与失败原因描述；
  - 异常操作日志中的描述信息避免包含敏感数据。

- 与既有规约对齐：
  - 命令不依赖查询/防腐层，查询不依赖仓储；
  - 日志写入命令仅通过仓储访问聚合或直接操作读模型表；
  - 统计逻辑可在订阅者中编排：查询 + 命令。
