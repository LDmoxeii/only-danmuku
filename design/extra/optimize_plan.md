     ╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────╮
     │ 用户行为流程代码优化计划（方案A：多命令模式）                                                                │
     │                                                                                                              │
     │ 📋 严格依照文档要求                                                                                          │
     │                                                                                                              │
     │ 按照 user_action_flow.md 文档，将单一的 DoActionCmd 重构为 5 个独立命令，实现类型安全、意图明确的 DDD 设计。 │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 🎯 核心设计原则（文档第 669-693 行）                                                                         │
     │                                                                                                              │
     │ 1. 意图明确：LikeVideoCmd vs DoActionCmd(actionType=2)                                                       │
     │ 2. 类型安全：每个命令明确参数，避免冗余                                                                      │
     │ 3. 单一职责：每个 Handler 只处理一种行为                                                                     │
     │ 4. 易于扩展：新增行为不影响已有代码                                                                          │
     │ 5. 符合 DDD：命令即领域语言                                                                                  │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 📦 实现步骤                                                                                                  │
     │                                                                                                              │
     │ 阶段一：基础设施（查询 + 验证器）                                                                            │
     │                                                                                                              │
     │ 1. 创建 GetUserActionByVideoQry 查询 ✅                                                                       │
     │                                                                                                              │
     │ 文件:                                                                                                        │
     │ - application/queries/customer_action/GetUserActionByVideoQry.kt                                             │
     │ - adapter/application/queries/customer_action/GetUserActionByVideoQryHandler.kt                              │
     │                                                                                                              │
     │ 功能: 查询用户对视频/评论的已有行为（幂等性检查）                                                            │
     │ 参数:                                                                                                        │
     │ data class Request(                                                                                          │
     │     val customerId: Long,                                                                                    │
     │     val videoId: Long,                                                                                       │
     │     val commentId: Long? = null,                                                                             │
     │     val actionType: Int? = null                                                                              │
     │ ) : RequestParam<Response>                                                                                   │
     │                                                                                                              │
     │ data class Response(                                                                                         │
     │     val hasAction: Boolean,                                                                                  │
     │     val action: CustomerActionDetail? = null                                                                 │
     │ )                                                                                                            │
     │                                                                                                              │
     │ Handler 逻辑: 使用 Jimmer 查询 JCustomerAction，返回是否存在                                                 │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 2. 创建 CheckUserCoinBalanceQry 查询 ✅                                                                       │
     │                                                                                                              │
     │ 文件:                                                                                                        │
     │ - application/queries/customer_profile/CheckUserCoinBalanceQry.kt                                            │
     │ - adapter/application/queries/customer_profile/CheckUserCoinBalanceQryHandler.kt                             │
     │                                                                                                              │
     │ 功能: 检查用户硬币余额是否充足                                                                               │
     │ 参数:                                                                                                        │
     │ data class Request(                                                                                          │
     │     val userId: Long,                                                                                        │
     │     val requiredAmount: Int                                                                                  │
     │ ) : RequestParam<Response>                                                                                   │
     │                                                                                                              │
     │ data class Response(                                                                                         │
     │     val sufficient: Boolean,                                                                                 │
     │     val currentBalance: Int                                                                                  │
     │ )                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 3. 创建验证器（3个）                                                                                         │
     │                                                                                                              │
     │ 3.1 @NotSelfCoin ✅                                                                                           │
     │                                                                                                              │
     │ 文件: application/validater/NotSelfCoin.kt                                                                   │
     │ 功能: 验证UP主不能给自己投币                                                                                 │
     │ 依赖: GetVideoInfoQry                                                                                        │
     │ 实现:                                                                                                        │
     │ @Target(AnnotationTarget.CLASS)                                                                              │
     │ annotation class NotSelfCoin(                                                                                │
     │     val message: String = "不能给自己的视频投币",                                                            │
     │     val userIdField: String = "customerId",                                                                  │
     │     val videoIdField: String = "videoId"                                                                     │
     │ )                                                                                                            │
     │                                                                                                              │
     │ 3.2 @NotDuplicateCoin ✅                                                                                      │
     │                                                                                                              │
     │ 文件: application/validater/NotDuplicateCoin.kt                                                              │
     │ 功能: 验证同一视频只能投币一次                                                                               │
     │ 依赖: GetUserActionByVideoQry                                                                                │
     │                                                                                                              │
     │ 3.3 @SufficientCoinBalance ✅                                                                                 │
     │                                                                                                              │
     │ 文件: application/validater/SufficientCoinBalance.kt                                                         │
     │ 功能: 验证用户硬币余额充足                                                                                   │
     │ 依赖: CheckUserCoinBalanceQry                                                                                │
     │ 实现:                                                                                                        │
     │ @Target(AnnotationTarget.CLASS)                                                                              │
     │ annotation class SufficientCoinBalance(                                                                      │
     │     val message: String = "硬币余额不足",                                                                    │
     │     val userIdField: String = "customerId",                                                                  │
     │     val coinCountField: String = "coinCount"                                                                 │
     │ )                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 阶段二：核心命令实现（5个命令）                                                                              │
     │                                                                                                              │
     │ 4. LikeVideoCmd - 点赞视频（Toggle 逻辑）✅                                                                   │
     │                                                                                                              │
     │ 文件: application/commands/customer_action/LikeVideoCmd.kt                                                   │
     │                                                                                                              │
     │ Request:                                                                                                     │
     │ data class Request(                                                                                          │
     │     @field:VideoExists                                                                                       │
     │     val videoId: Long,                                                                                       │
     │     val customerId: Long                                                                                     │
     │ ) : RequestParam<Response>                                                                                   │
     │                                                                                                              │
     │ Handler 逻辑（文档第 50-101 行）:                                                                            │
     │ 1. 查询视频信息 GetVideoInfoQry                                                                              │
     │ 2. 查询已有点赞记录 GetUserActionByVideoQry(actionType=LIKE_VIDEO)                                           │
     │ 3. Toggle 逻辑:                                                                                              │
     │   - 已点赞 → 删除记录（Mediator.repositories.delete(action)）                                                │
     │   - 未点赞 → 创建记录（CustomerAction.create(...)）                                                          │
     │ 4. 发布领域事件 CustomerLikedVideoDomainEvent(isCancel=true/false)                                           │
     │ 5. Mediator.uow.save()                                                                                       │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 5. CollectVideoCmd - 收藏视频（Toggle 逻辑）✅                                                                │
     │                                                                                                              │
     │ 文件: application/commands/customer_action/CollectVideoCmd.kt                                                │
     │                                                                                                              │
     │ Request:                                                                                                     │
     │ data class Request(                                                                                          │
     │     @field:VideoExists                                                                                       │
     │     val videoId: Long,                                                                                       │
     │     val customerId: Long                                                                                     │
     │ ) : RequestParam<Response>                                                                                   │
     │                                                                                                              │
     │ Handler 逻辑（文档第 105-146 行）: 同 LikeVideoCmd，触发 CustomerCollectedVideoDomainEvent                   │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 6. GiveVideoCoinCmd - 投币视频（一次性逻辑）✅                                                                │
     │                                                                                                              │
     │ 文件: application/commands/customer_action/GiveVideoCoinCmd.kt                                               │
     │                                                                                                              │
     │ Request:                                                                                                     │
     │ @NotSelfCoin(userIdField = "customerId", videoIdField = "videoId")                                           │
     │ @NotDuplicateCoin(userIdField = "customerId", videoIdField = "videoId")                                      │
     │ @SufficientCoinBalance(userIdField = "customerId", coinCountField = "coinCount")                             │
     │ data class Request(                                                                                          │
     │     @field:VideoExists                                                                                       │
     │     val videoId: Long,                                                                                       │
     │     val customerId: Long,                                                                                    │
     │     @field:Min(1) @field:Max(2)                                                                              │
     │     val coinCount: Int                                                                                       │
     │ ) : RequestParam<Response>                                                                                   │
     │                                                                                                              │
     │ Handler 逻辑（文档第 150-227 行）:                                                                           │
     │ 1. 查询视频信息 GetVideoInfoQry                                                                              │
     │ 2. 查询已有投币记录（验证器已保证不重复）                                                                    │
     │ 3. 业务规则校验（验证器已完成）                                                                              │
     │ 4. 创建投币记录 CustomerAction.create(actionType=COIN_VIDEO, actionCount=coinCount)                          │
     │ 5. 发布领域事件 CustomerCoinGivenDomainEvent(customerId, videoId, videoUserId, coinCount)                    │
     │ 6. Mediator.uow.save()                                                                                       │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 7. LikeCommentCmd - 点赞评论（互斥逻辑）✅                                                                    │
     │                                                                                                              │
     │ 文件: application/commands/customer_action/LikeCommentCmd.kt                                                 │
     │                                                                                                              │
     │ Request:                                                                                                     │
     │ data class Request(                                                                                          │
     │     @field:VideoExists                                                                                       │
     │     val videoId: Long,                                                                                       │
     │     @field:CommentExists                                                                                     │
     │     val commentId: Long,                                                                                     │
     │     val customerId: Long                                                                                     │
     │ ) : RequestParam<Response>                                                                                   │
     │                                                                                                              │
     │ Handler 逻辑（文档第 231-294 行）:                                                                           │
     │ 1. 查询视频信息 GetVideoInfoQry                                                                              │
     │ 2. 查询评论信息 GetCommentByIdQry                                                                            │
     │ 3. 查询已有行为（点赞/点踩）                                                                                 │
     │ 4. 互斥逻辑:                                                                                                 │
     │   - 已点赞 → 删除点赞记录（取消点赞）                                                                        │
     │   - 已点踩 → 删除点踩记录 + 创建点赞记录（切换）                                                             │
     │   - 无操作 → 创建点赞记录（新增点赞）                                                                        │
     │ 5. 发布事件 CustomerLikedCommentDomainEvent(isCancel, hadOpposite)                                           │
     │ 6. Mediator.uow.save()                                                                                       │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 8. DislikeCommentCmd - 点踩评论（互斥逻辑）✅                                                                 │
     │                                                                                                              │
     │ 文件: application/commands/customer_action/DislikeCommentCmd.kt                                              │
     │                                                                                                              │
     │ Request: 同 LikeCommentCmd                                                                                   │
     │                                                                                                              │
     │ Handler 逻辑: 同上，触发 CustomerDislikedCommentDomainEvent                                                  │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 阶段三：事件处理器（5个）                                                                                    │
     │                                                                                                              │
     │ 9. CustomerLikedVideoEventHandler ✅                                                                          │
     │                                                                                                              │
     │ 文件: adapter/domain/events/customer_action/CustomerLikedVideoEventHandler.kt                                │
     │                                                                                                              │
     │ 监听: CustomerLikedVideoDomainEvent                                                                          │
     │                                                                                                              │
     │ 逻辑:                                                                                                        │
     │ @EventListener                                                                                               │
     │ fun handle(event: CustomerLikedVideoDomainEvent) {                                                           │
     │     val video = Mediator.repositories.findFirst(                                                             │
     │         SVideo.predicateById(event.videoId)                                                                  │
     │     ).get()                                                                                                  │
     │                                                                                                              │
     │     val change = if (event.isCancel) -1 else +1                                                              │
     │     video.updateLikeCount(change) // 调用聚合方法                                                            │
     │                                                                                                              │
     │     Mediator.uow.save()                                                                                      │
     │ }                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 10. CustomerCollectedVideoEventHandler ✅                                                                     │
     │                                                                                                              │
     │ 文件: adapter/domain/events/customer_action/CustomerCollectedVideoEventHandler.kt                            │
     │                                                                                                              │
     │ 监听: CustomerCollectedVideoDomainEvent                                                                      │
     │                                                                                                              │
     │ 逻辑: 同上，更新 collectCount                                                                                │
     │                                                                                                              │
     │ 可选: 触发 UpdateElasticsearchCmd 更新 ES 收藏数                                                             │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 11. CustomerCoinGivenEventHandler ✅（核心）                                                                  │
     │                                                                                                              │
     │ 文件: adapter/domain/events/customer_action/CustomerCoinGivenEventHandler.kt                                 │
     │                                                                                                              │
     │ 监听: CustomerCoinGivenDomainEvent                                                                           │
     │                                                                                                              │
     │ 逻辑（文档第 193-227 行）:                                                                                   │
     │ @EventListener                                                                                               │
     │ fun handle(event: CustomerCoinGivenDomainEvent) {                                                            │
     │     // 1. 硬币转账                                                                                           │
     │     val sender = Mediator.repositories.findFirst(                                                            │
     │         SCustomerProfile.predicate { it.userId eq event.customerId }                                         │
     │     ).get()                                                                                                  │
     │                                                                                                              │
     │     val receiver = Mediator.repositories.findFirst(                                                          │
     │         SCustomerProfile.predicate { it.userId eq event.videoUserId }                                        │
     │     ).get()                                                                                                  │
     │                                                                                                              │
     │     sender.transferCoin(receiver, event.coinCount) // 调用聚合方法                                           │
     │                                                                                                              │
     │     // 2. 更新视频投币数                                                                                     │
     │     val video = Mediator.repositories.findFirst(                                                             │
     │         SVideo.predicateById(event.videoId)                                                                  │
     │     ).get()                                                                                                  │
     │                                                                                                              │
     │     video.updateCoinCount(event.coinCount)                                                                   │
     │                                                                                                              │
     │     Mediator.uow.save()                                                                                      │
     │ }                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 12. CustomerLikedCommentEventHandler ✅                                                                       │
     │                                                                                                              │
     │ 文件: adapter/domain/events/customer_action/CustomerLikedCommentEventHandler.kt                              │
     │                                                                                                              │
     │ 监听: CustomerLikedCommentDomainEvent                                                                        │
     │                                                                                                              │
     │ 逻辑（文档第 269-286 行）:                                                                                   │
     │ @EventListener                                                                                               │
     │ fun handle(event: CustomerLikedCommentDomainEvent) {                                                         │
     │     val comment = Mediator.repositories.findFirst(                                                           │
     │         SVideoComment.predicateById(event.commentId)                                                         │
     │     ).get()                                                                                                  │
     │                                                                                                              │
     │     val likeChange = if (event.isCancel) -1 else +1                                                          │
     │     val hateChange = if (event.hadOpposite) -1 else 0                                                        │
     │                                                                                                              │
     │     comment.updateStatistics(likeChange, hateChange)                                                         │
     │                                                                                                              │
     │     Mediator.uow.save()                                                                                      │
     │ }                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 13. CustomerDislikedCommentEventHandler ✅                                                                    │
     │                                                                                                              │
     │ 文件: adapter/domain/events/customer_action/CustomerDislikedCommentEventHandler.kt                           │
     │                                                                                                              │
     │ 监听: CustomerDislikedCommentDomainEvent                                                                     │
     │                                                                                                              │
     │ 逻辑: 同上（like/hate 互换）                                                                                 │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 阶段四：聚合根方法补充                                                                                       │
     │                                                                                                              │
     │ 14. CustomerAction 聚合根 ✅                                                                                  │
     │                                                                                                              │
     │ 文件: domain/aggregates/customer_action/CustomerAction.kt                                                    │
     │                                                                                                              │
     │ 新增方法:                                                                                                    │
     │ // 【行为方法开始】                                                                                          │
     │                                                                                                              │
     │ companion object {                                                                                           │
     │     fun create(                                                                                              │
     │         customerId: Long,                                                                                    │
     │         videoId: Long,                                                                                       │
     │         videoOwnerId: Long,                                                                                  │
     │         actionType: ActionType,                                                                              │
     │         actionCount: Int = 1,                                                                                │
     │         commentId: Long = 0L                                                                                 │
     │     ): CustomerAction {                                                                                      │
     │         val action = CustomerAction(                                                                         │
     │             customerId = customerId.toString(),                                                              │
     │             videoId = videoId.toString(),                                                                    │
     │             videoOwnerId = videoOwnerId.toString(),                                                          │
     │             commentId = commentId,                                                                           │
     │             actionType = actionType,                                                                         │
     │             actionCount = actionCount,                                                                       │
     │             actionTime = System.currentTimeMillis() / 1000                                                   │
     │         )                                                                                                    │
     │                                                                                                              │
     │         // 发布领域事件                                                                                      │
     │         when (actionType) {                                                                                  │
     │             ActionType.LIKE_VIDEO ->                                                                         │
     │                 action.publishDomainEvent(CustomerLikedVideoDomainEvent(...))                                │
     │             ActionType.FAVORITE_VIDEO ->                                                                     │
     │                 action.publishDomainEvent(CustomerCollectedVideoDomainEvent(...))                            │
     │             ActionType.COIN_VIDEO ->                                                                         │
     │                 action.publishDomainEvent(CustomerCoinGivenDomainEvent(...))                                 │
     │             ActionType.LIKE_COMMENT ->                                                                       │
     │                 action.publishDomainEvent(CustomerLikedCommentDomainEvent(...))                              │
     │             ActionType.HATE_COMMENT ->                                                                       │
     │                 action.publishDomainEvent(CustomerDislikedCommentDomainEvent(...))                           │
     │         }                                                                                                    │
     │                                                                                                              │
     │         return action                                                                                        │
     │     }                                                                                                        │
     │ }                                                                                                            │
     │                                                                                                              │
     │ // 【行为方法结束】                                                                                          │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 15. Video 聚合根 ✅                                                                                           │
     │                                                                                                              │
     │ 文件: domain/aggregates/video/Video.kt                                                                       │
     │                                                                                                              │
     │ 新增方法:                                                                                                    │
     │ fun updateLikeCount(change: Int) {                                                                           │
     │     this.likeCount = (this.likeCount ?: 0) + change                                                          │
     │ }                                                                                                            │
     │                                                                                                              │
     │ fun updateCollectCount(change: Int) {                                                                        │
     │     this.collectCount = (this.collectCount ?: 0) + change                                                    │
     │ }                                                                                                            │
     │                                                                                                              │
     │ fun updateCoinCount(change: Int) {                                                                           │
     │     this.coinCount = (this.coinCount ?: 0) + change                                                          │
     │ }                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 16. VideoComment 聚合根 ✅                                                                                    │
     │                                                                                                              │
     │ 文件: domain/aggregates/video_comment/VideoComment.kt                                                        │
     │                                                                                                              │
     │ 新增方法:                                                                                                    │
     │ fun updateStatistics(likeChange: Int, hateChange: Int) {                                                     │
     │     this.likeCount = (this.likeCount ?: 0) + likeChange                                                      │
     │     this.hateCount = (this.hateCount ?: 0) + hateChange                                                      │
     │ }                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 17. CustomerProfile 聚合根 ✅                                                                                 │
     │                                                                                                              │
     │ 文件: domain/aggregates/customer_profile/CustomerProfile.kt                                                  │
     │                                                                                                              │
     │ 新增方法:                                                                                                    │
     │ fun transferCoin(toProfile: CustomerProfile, amount: Int) {                                                  │
     │     require(this.currentCoinCount >= amount) { "硬币余额不足" }                                              │
     │     this.currentCoinCount -= amount                                                                          │
     │     toProfile.currentCoinCount += amount                                                                     │
     │ }                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 阶段五：Controller 层重构                                                                                    │
     │                                                                                                              │
     │ 18. 重构 Controller 路由分发 ✅                                                                               │
     │                                                                                                              │
     │ 文件: adapter/portal/api/UserActionController.kt                                                             │
     │                                                                                                              │
     │ 实现（文档第 610-664 行）:                                                                                   │
     │ @PostMapping("/doAction")                                                                                    │
     │ fun doAction(@RequestBody @Validated request: DoActionRequest): Response {                                   │
     │     val customerId = LoginHelper.getUserId()!!                                                               │
     │                                                                                                              │
     │     when (request.actionType) {                                                                              │
     │         UserActionType.VIDEO_LIKE ->                                                                         │
     │             Mediator.commands.send(                                                                          │
     │                 LikeVideoCmd.Request(                                                                        │
     │                     customerId = customerId,                                                                 │
     │                     videoId = request.videoId                                                                │
     │                 )                                                                                            │
     │             )                                                                                                │
     │                                                                                                              │
     │         UserActionType.VIDEO_COLLECT ->                                                                      │
     │             Mediator.commands.send(                                                                          │
     │                 CollectVideoCmd.Request(                                                                     │
     │                     customerId = customerId,                                                                 │
     │                     videoId = request.videoId                                                                │
     │                 )                                                                                            │
     │             )                                                                                                │
     │                                                                                                              │
     │         UserActionType.VIDEO_COIN ->                                                                         │
     │             Mediator.commands.send(                                                                          │
     │                 GiveVideoCoinCmd.Request(                                                                    │
     │                     customerId = customerId,                                                                 │
     │                     videoId = request.videoId,                                                               │
     │                     coinCount = request.actionCount ?: 1                                                     │
     │                 )                                                                                            │
     │             )                                                                                                │
     │                                                                                                              │
     │         UserActionType.COMMENT_LIKE ->                                                                       │
     │             Mediator.commands.send(                                                                          │
     │                 LikeCommentCmd.Request(                                                                      │
     │                     customerId = customerId,                                                                 │
     │                     videoId = request.videoId,                                                               │
     │                     commentId = request.commentId!!                                                          │
     │                 )                                                                                            │
     │             )                                                                                                │
     │                                                                                                              │
     │         UserActionType.COMMENT_HATE ->                                                                       │
     │             Mediator.commands.send(                                                                          │
     │                 DislikeCommentCmd.Request(                                                                   │
     │                     customerId = customerId,                                                                 │
     │                     videoId = request.videoId,                                                               │
     │                     commentId = request.commentId!!                                                          │
     │                 )                                                                                            │
     │             )                                                                                                │
     │     }                                                                                                        │
     │                                                                                                              │
     │     return Response()                                                                                        │
     │ }                                                                                                            │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 📝 实现顺序                                                                                                  │
     │                                                                                                              │
     │ 1. ✅ 创建 2 个查询（GetUserActionByVideoQry, CheckUserCoinBalanceQry）                                       │
     │ 2. ✅ 创建 3 个验证器（@NotSelfCoin, @NotDuplicateCoin, @SufficientCoinBalance）                              │
     │ 3. ✅ 创建 5 个命令（LikeVideoCmd, CollectVideoCmd, GiveVideoCoinCmd, LikeCommentCmd, DislikeCommentCmd）     │
     │ 4. ✅ 补充聚合根方法（4个聚合根）                                                                             │
     │ 5. ✅ 创建 5 个事件处理器                                                                                     │
     │ 6. ✅ 重构 Controller 层路由分发                                                                              │
     │ 7. 🧪 创建 HTTP 测试文件                                                                                     │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ ⚠️ 关键注意事项                                                                                              │
     │                                                                                                              │
     │ 1. 字段类型转换: CustomerAction 使用 String 类型的 customerId/videoId，需转换                                │
     │ 2. 事件载荷:                                                                                                 │
     │   - isCancel: 区分新增/取消                                                                                  │
     │   - hadOpposite: 是否移除了相反操作                                                                          │
     │   - coinCount: 投币数量                                                                                      │
     │ 3. 事务边界: 命令处理 + 事件发布 = 一个事务；事件处理器 = 新事务                                             │
     │ 4. 废弃旧代码: DoActionCmd 标记为 @Deprecated，后续删除                                                      │
     │                                                                                                              │
     │ ---                                                                                                          │
     │ 📊 预期收益（文档第 669-693 行）                                                                             │
     │                                                                                                              │
     │ 1. ✅ 意图明确: 命令名称即业务含义                                                                            │
     │ 2. ✅ 类型安全: 避免参数冗余和错误                                                                            │
     │ 3. ✅ 单一职责: 每个 Handler 职责清晰                                                                         │
     │ 4. ✅ 易于扩展: 符合开闭原则                                                                                  │
     │ 5. ✅ 符合 DDD: 命令即领域语言
每个事件都有事件监听器， 然后事件监听器相当于一个内部的命令发送器， 所以事件处理器的逻辑应该是命令的行为，
而事件监听器的作用就是发出命令
