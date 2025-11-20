# 账号安全与手机号支持需求说明

## 一、背景与目标

当前 only-danmuku 平台账号体系以邮箱 + 密码登录为主：
- `user` 表保存邮箱 `email`、密码 `password` 等账号级字段，暂无手机号；
- `customer_profile` 表保存用户昵称、头像、邮箱、硬币等资料，同样暂无手机号；
- 登录流程由 `CompatibleAccountController.login` → `UpdateLoginInfoCmd` → `User.updateLoginInfo` 完成，并记录最后登录时间/IP；
- 已集成图形验证码 `CaptchaGenCli` / `CaptchaValidCli`，用于登录/注册防刷。

Issue #3 希望增强账号安全能力，并引入手机号维度，具体包括：
1. 新增“修改密码”逻辑（接口、命令、事件及聚合行为）；
2. 支持手机号信息的记录与展示，评估如何在 `user` 与 `customer_profile` 中存储，并通过事件保持一致；
3. 评估并设计“手机号登录”能力，并通过新增接口方式扩展（如独立的 `/account/loginBySms` 接口）；
4. 评估并设计“手机验证码发送/校验”能力，与现有图形验证码配合使用。

本次设计作为需求说明文档，对本迭代需要完成的范围进行约束，并为后续 DDD 设计与代码生成提供输入。

## 二、本次迭代范围

### 2.1 必做项

1. 新增修改密码能力
   - 提供前台用户自助修改密码接口：`POST /account/changePassword`；
   - 修改密码需要验证“原密码”正确性 + 新密码复杂度；
   - 修改密码应触发领域事件，用于记录安全日志或下游订阅（如强制下线、通知等）。

2. 支持手机号信息记录
   - 在 `user` 与 `customer_profile` 两张表中均新增手机号相关字段（详见第 4 章）；
   - 历史数据初始化方案：
     - 现有数据手机号字段统一置空；
     - 后续由用户手工绑定或通过运维脚本批量导入。

3. 登录体系优化（引入手机号维度 + 新接口）
   - 在保持现有“邮箱 + 密码登录”（`POST /account/login`）不变的前提下，新增“手机号 + 短信验证码登录”（`POST /account/loginBySms`）；
   - `/account/login` 仍采用密码登录流程（图形验证码 + `UpdateLoginInfoCmd`），可在后续迭代中支持“邮箱/手机号任一作为 loginId”；
   - `/account/loginBySms` 作为全新接口，内部直接编排“短信验证码校验 + 根据手机号获取账号 + 更新登录信息”链路，通过独立接口实现扩展。

4. 手机验证码发送与校验能力
   - 提供获取短信验证码接口：`POST /account/sendSmsCode`；
   - 提供使用短信验证码登录/绑定的校验接口：`POST /account/loginBySms`、`POST /account/bindPhone` 等；
   - 需要设计统一的“短信发送 Client + Handler”，屏蔽短信服务商实现；
   - 需要设计“短信验证码生成/校验命令”，并具备基础防刷规则：
     - 同一手机号单位时间内发送频率限制；
     - 同一客户端/账号单位时间内错误次数限制。

### 2.2 可选/后续迭代项

1. 修改密码后的强制下线与 Token 失效机制；
2. 密码复杂度规则可配置化（如加入特殊字符要求）；
3. 手机号找回密码流程（通过短信重置密码）；
4. 管理端对手机号的查询与风控管理能力。

## 三、修改密码需求细化

### 3.1 接口设计

- 路径：`POST /account/changePassword`
- 鉴权：登录态必需（Sa-Token）；
- 入参：
  - `oldPassword`：原密码；
  - `newPassword`：新密码；
- 出参：空或简单结果对象（`{ success: true }`）。

### 3.2 应用命令与领域行为

- 新增应用命令：`ChangePasswordCmd`（package: `user`）：
  - Request：
    - `userId`（从当前登录上下文获取）；
    - `oldPassword`；
    - `newPassword`；
  - Handler 核心逻辑：
    1. 根据 `userId` 从 `User` 聚合仓储加载实体；
    2. 调用 `User.verifyPassword(oldPassword)` 校验原密码；
    3. 调用 `User.changePassword(newPassword)` 完成密码更新与哈希；
    4. 触发领域事件 `PasswordChangedDomainEvent`；
    5. `Mediator.uow.save()` 提交。

- 聚合行为扩展（`User`）：
  - 新增方法 `verifyPassword(rawPassword: String): Boolean`：统一密码验证逻辑；
  - 新增方法 `changePassword(newRawPassword: String)`：
    - 按安全规则对 `newRawPassword` 做哈希存储；
    - 更新 `password` 字段；
    - 发布 `PasswordChangedDomainEvent` 事件。

### 3.3 事件与订阅

- 领域事件：`PasswordChangedDomainEvent`（aggregate: `User`，entity: `User`）；
- 订阅者（本迭代可先记录日志，后续扩展）：
  - `PasswordChangedDomainEventSubscriber`：
    - 记录安全日志；
    - 未来可扩展为：
      - 触发“强制下线”命令；
      - 发送站内信/邮件通知。

## 四、手机号字段与数据库设计

### 4.1 字段落库位置决策

- 选项 A：仅在 `user` 表新增手机号字段：
  - 优点：登录凭据集中（邮箱/手机号统一），认证逻辑简单；
  - 缺点：与 `customer_profile` 的展示信息存在割裂，需要额外查询合并。

- 选项 B：仅在 `customer_profile` 表新增手机号字段：
  - 优点：
    - 与头像、昵称、邮箱等“用户对外信息”保持一致；
    - 可通过查询模型直接渲染前端；
  - 缺点：登录校验仍依赖 `user`，需要额外关联或冗余。

- 选项 C：**在 `user` 与 `customer_profile` 两张表都维护手机号字段，并通过领域事件保持一致**：
  - 优点：
    - `user` 作为认证实体，直接使用手机号进行登录校验；
    - `customer_profile` 作为用户展示实体，也能直接展示手机号；
    - 利用事件同步，解耦“展示/编辑入口”和“认证凭据存储”。
  - 缺点：
    - 双表冗余，需要额外的同步逻辑和演进方案。

> 本次迭代选用 **选项 C：`user` 与 `customer_profile` 同时新增手机号字段，并通过领域事件同步**。

### 4.2 表结构变更

在 `user` 表新增：

- `phone` varchar(20) NULL DEFAULT NULL COMMENT '手机号';
- 唯一索引：`UNIQUE INDEX uk_v_phone (phone, deleted)`。

在 `customer_profile` 表新增同名字段，用于前台展示与编辑：

- `phone` varchar(20) NULL DEFAULT NULL COMMENT '手机号';
- 唯一索引：`UNIQUE INDEX uk_v_phone (phone, deleted)`。

### 4.3 历史数据初始化

- 迁移脚本方案：
  - 已有数据：
    - `phone` 置为 NULL；
  - 不做手机号自动推断（避免误判），统一由用户手工绑定；
  - `user` 与 `customer_profile` 历史数据通过一次性批处理进行对齐（以邮箱/关联关系为准，当前批次可先全部置空）。

### 4.4 登录流程优化

- 密码登录接口（已有）：`POST /account/login`
  - 输入：`email`（当前仍为邮箱，后续可扩展为“邮箱/手机号任一 loginId”）、`password`、`checkCodeKey`、`checkCode`；
  - 行为：
    - 通过 `CaptchaValidCli` 校验图形验证码；
    - 调用 `UpdateLoginInfoCmd` 完成账号加载、密码校验和登录信息更新；
    - 底层触发既有 `LoginInfoUpdatedDomainEvent`。

- 短信登录接口（新增）：`POST /account/loginBySms`
  - 输入：`phone`、`smsCode`；
  - 行为：
    - 通过 `ValidateSmsCodeCmd` 校验短信验证码；
    - 通过 `GetUserByPhoneQry` 根据手机号定位账号（底层依赖 `user.phone` 字段）；
    - 调用 `UpdateLoginInfoCmd` 统一更新登录信息和触发登录事件。

## 五、短信验证码发送与校验

### 5.1 业务场景

1. 绑定/变更手机号时，向目标手机号发送验证码；
2. 支持“手机号 + 短信验证码登录”；
3. 后续可扩展到“找回密码”等场景。

### 5.2 接口设计

1. 发送短信验证码
   - 路径：`POST /account/sendSmsCode`
   - 入参：
     - `scene`：验证码场景（如 `BIND_PHONE`, `LOGIN`）；
     - `phone`：目标手机号；
     - `captchaKey` / `captchaCode`：图形验证码（防刷）。
   - 出参：`{ success: true }`。

2. 使用短信验证码登录
   - 路径：`POST /account/loginBySms`
   - 入参：
     - `phone`：手机号；
     - `smsCode`：短信验证码；
   - 行为：
     - 通过防腐层 `ValidateSmsCodeCli` 校验验证码；
     - 通过 `GetUserByPhoneQry` 定位 `user` & `customer_profile`；
     - 统一通过 `UpdateLoginInfoCmd` 更新登录信息并触发事件。

3. 绑定/变更手机号
   - 路径：`POST /account/bindPhone`
   - 入参：
     - `phone`：新手机号；
     - `smsCode`：验证码；
   - 行为：
     - 验证短信验证码（通过防腐层 `ValidateSmsCodeCli`）；
     - 校验手机号唯一性；
     - 更新 `customer_profile.phone`；
     - 通过领域事件同步更新 `user.phone`。

### 5.3 DDD 设计要点

1. 分布式 Client（发送短信）
   - 新增 `SmsSendCli` / `SmsSendCliHandler`：
     - Request：`phone`, `templateCode`, `params`, `scene`；
     - Response：发送结果标记；
   - 由 adapter 层 Handler 调用第三方短信网关/服务端配置。

2. 验证码生成与校验
   - 设计分布式 Client：
     - `GenerateSmsCodeCli`：生成并持久化验证码并触发发送逻辑，负责限流与过期时间控制；
     - `ValidateSmsCodeCli`：校验验证码并按需作废，记录错误次数。
   - 存储可基于 Redis 或数据库，根据现有基础设施选型。

3. 手机号同步事件流
   - 在 `CustomerProfile.bindPhone` 中发布 `CustomerProfilePhoneChangedDomainEvent`；
   - 订阅该事件的命令 `SyncUserPhoneFromCustomerProfileCmd` 负责：
     - 根据 `userId` 加载 `User` 聚合；
     - 调用 `User.syncPhoneFromProfile(phone, verified)` 更新账号侧手机号；
     - 触发 `UserPhoneSyncedDomainEvent`（可选，用于审计）。

## 六、非功能需求与约束

- 安全：
  - 密码必须采用安全哈希存储，禁止明文；
  - 手机验证码有效时间宜控制在 5 分钟以内；
  - 短信发送需要基础频控与审计日志。

- 兼容性：
  - 不破坏现有邮箱登录与注册流程；
  - 不影响现有 `user` / `customer_profile` 其他读写接口。

- 可运维性：
  - 表结构变更需提供独立 SQL 脚本，支持回滚方案说明；
  - 短信渠道配置（AK/SK、签名、模板）统一集中在配置中心。

## 七、本次迭代交付物总结

1. SQL 设计
   - 在 `Iterate/account_security_phone_update.sql` 中提供针对现有库结构的增量更新脚本（为 `user` / `customer_profile` 新增手机号字段及索引），不直接修改 `design/sql` 下的建表文件；
   - 若需要，为手机号登录/查询新增视图或辅助索引，可在后续迭代的迁移脚本中补充。

2. DDD 设计元素
   - 在 `Iterate/account_security_gen.json` 中新增：
     - 命令：`ChangePasswordCmd`、`BindPhoneCmd`、`SyncUserPhoneFromCustomerProfileCmd`；
     - 查询：`GetUserByPhoneQry`；
     - 分布式 Client：`GenerateSmsCodeCli`、`ValidateSmsCodeCli`、`SmsSendCli`；
     - 领域事件：`PasswordChangedDomainEvent`、`CustomerProfilePhoneChangedDomainEvent`、`UserPhoneSyncedDomainEvent`。
   - 作为代码生成的输入，后续由 `./gradlew genDesign` 产出命令/查询/Client 骨架。

3. 流程分析文档
   - 本次迭代在子目录 `Iterate/account-security-and-phone` 下拆分为 4 份独立流程图：
     - `CompatibleAccountController_changePassword.mmd`：修改密码流程（入口 → ChangePasswordCmd → User.changePassword → PasswordChangedDomainEvent）；
     - `CompatibleUHomeController_bindPhone.mmd`：绑定手机号并通过事件同步 User 流程；
     - `CompatibleAccountController_loginBySms.mmd`：手机号 + 短信验证码登录流程（入口 → ValidateSmsCodeCli → GetUserByPhoneQry → UpdateLoginInfoCmd → LoginInfoUpdatedDomainEvent）；
     - `CompatibleAccountController_sendSmsCode.mmd`：发送短信验证码流程（入口 → SmsSendCli）。
   - 各流程均采用“命令入口 →（命令 → 聚合领域方法 → 事件）→ …”的串联形式，对齐 `analysis` 目录既有风格。
