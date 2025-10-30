# easylive-java 用户消息发送点位梳理

本文档汇总 easylive-java 项目中“用户消息”的触发入口、AOP 链路、发送实现与相关存储。

**触发入口（控制器标注）**
- 点赞/收藏等用户行为触发
  - `easylive-java/easylive-web/src/main/java/com/easylive/web/controller/UserActionController.java:29`
    - 注解：`@RecordUserMessage(messageType = MessageTypeEnum.LIKE)`，方法 `/userAction/doAction`
- 评论发布触发
  - `easylive-java/easylive-web/src/main/java/com/easylive/web/controller/VideoCommentController.java:171`
    - 注解：`@RecordUserMessage(messageType = MessageTypeEnum.COMMENT)`，方法 `/comment/postComment`
- 后台审核结果系统消息触发
  - `easylive-java/easylive-admin/src/main/java/com/easylive/admin/controller/VideoInfoController.java:57`
    - 注解：`@RecordUserMessage(messageType = MessageTypeEnum.SYS)`，方法 `/videoInfo/auditVideo`

**AOP 注解链路**
- 自定义注解定义
  - `easylive-java/easylive-common/src/main/java/com/easylive/annotation/RecordUserMessage.java:1`
- 切面（当前仅记录日志，未直接调用发送服务）
  - `easylive-java/easylive-common/src/main/java/com/easylive/aspect/UserMessageOperationAspect.java:21`
  - 说明：通过 `@Around("@annotation(RecordUserMessage)")` 拦截，被拦截后仅打印开始/结束日志；若要打通“注解 → 实际发送”，需在此处注入 `UserMessageService` 并从 `joinPoint` 解析参数后调用发送方法（见下文）。

**发送实现（服务层）**
- 接口定义
  - `easylive-java/easylive-common/src/main/java/com/easylive/service/UserMessageService.java:1`
  - 关键方法：`saveUserMessage(String videoId, String sendUserId, MessageTypeEnum messageTypeEnum, String content, Integer replyCommentId)`（`…:74`）
- 实现类与主要逻辑
  - `easylive-java/easylive-common/src/main/java/com/easylive/service/impl/UserMessageServiceImpl.java:1`
  - `saveUserMessage(...)` 实现（`…:205` 起）：
    - 点赞/收藏去重：若存在相同 `sendUserId+videoId+type` 记录则跳过（`…:217` ~ `…:221`）
    - 计算接收方：默认视频作者；评论场景使用被回复用户（`…:241` ~ `…:247`）
    - 自发自收过滤：`userId.equals(sendUserId)` 则跳过（`…:252` ~ `…:257`）
    - 系统消息补充审核状态（`…:250` 附近）
    - 组装 `extendJson` 并插入（`…:255`），最终 `insert`（`…:257`）

**消息相关的 Mapper/SQL**
- `easylive-java/easylive-common/src/main/resources/com/easylive/mappers/UserMessageMapper.xml:113`（`insert`）
- 同文件：`insertOrUpdate`（`…:167`）、`insertBatch`（`…:248`）、`updateByMessageId`（`…:339`）、`getMessageTypeNoReadCount`（`…:379`）

**当前显式调用分布（检索结果）**
- 直接调用 `saveUserMessage(...)`：未检索到业务处显式调用；触发意图主要通过控制器方法上的 `@RecordUserMessage` 注解表达，需由切面承接。
- 用户消息查询/统计/删除等使用位置（非“发送”）：
  - `easylive-java/easylive-web/src/main/java/com/easylive/web/controller/UserMessageContrller.java:49`（未读数统计）
  - `easylive-java/easylive-web/src/main/java/com/easylive/web/controller/UserMessageContrller.java:61`（分组未读数）
  - `easylive-java/easylive-web/src/main/java/com/easylive/web/controller/UserMessageContrller.java:112`（分页列表）
  - `easylive-java/easylive-web/src/main/java/com/easylive/web/controller/UserMessageContrller.java:85`（标记已读）
  - `easylive-java/easylive-web/src/main/java/com/easylive/web/controller/UserMessageContrller.java:133`（删除）

**关联系统枚举与实体**
- 枚举：
  - `easylive-java/easylive-common/src/main/java/com/easylive/entity/enums/MessageTypeEnum.java:1`
  - `easylive-java/easylive-common/src/main/java/com/easylive/entity/enums/MessageReadTypeEnum.java:1`
- 实体：
  - `easylive-java/easylive-common/src/main/java/com/easylive/entity/po/UserMessage.java:1`
  - 扩展 DTO：`easylive-java/easylive-common/src/main/java/com/easylive/entity/dto/UserMessageExtendDto.java:1`

**改造建议（可选）**
- 在 `UserMessageOperationAspect` 中注入 `UserMessageService`，根据被拦截方法的参数（如 `videoId`、当前登录用户ID、评论内容/回复ID等）拼装并调用 `saveUserMessage(...)`，以实现注解即发送。
- 对点赞/收藏等不同动作，若要细分 `messageType` 映射，可在切面中根据 `actionType`（或方法名）进行枚举转换，而不是固定为 LIKE。

