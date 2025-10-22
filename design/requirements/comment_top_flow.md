# 视频评论置顶流程设计文档

> 基于 easylive-java 项目需求，按照 DDD 事件驱动模式设计

## 📋 业务需求概述
视频作者（或管理员）对某条评论进行置顶操作，使其在评论列表中优先展示。系统需校验评论存在性、确认评论属于目标视频，并验证操作者对该视频拥有管理权限。若系列中已有其他置顶评论，应取消原置顶后再设置新的置顶。

---

## 📊 完整流程图

### ASCII 流程图
```
┌──────────────────────────────────────────────────────────┐
│ 请求：POST /comment/topComment                            │
│ Payload: { "commentId": 345678 }                          │
└────────────────────────────┬─────────────────────────────┘
                             ↓
┌──────────────────────────────────────────────────────────┐
│ 控制器：CommentController#commentTop ✅                   │
│ 1. 解析登录用户 → operatorId                             │
│ 2. Mediator.commands.send(TopCommentCmd.Request)          │
└────────────────────────────┬─────────────────────────────┘
                             ↓
┌──────────────────────────────────────────────────────────┐
│ 命令：TopCommentCmd ✅                                     │
│ 1. 加载评论 & 视频信息                                    │
│ 2. 校验评论归属/视频作者权限                              │
│ 3. 若存在其他置顶 → commentRepository.untop(videoId) ✅    │
│ 4. 调用 VideoComment.top()（聚合行为）                     │
│ 5. Mediator.uow.save()                                     │
└────────────────────────────┬─────────────────────────────┘
                             ↓
┌──────────────────────────────────────────────────────────┐
│ 领域事件：CommentToppedDomainEvent ✅                     │
│ 由聚合在 onTop/onCreate 中自动发布                        │
└────────────────────────────┬─────────────────────────────┘
                             ↓
┌──────────────────────────────────────────────────────────┐
│ 事件监听器：CommentToppedDomainEventSubscriber ⚪         │
│ → 后续命令：RefreshVideoCommentTopCmd ❌（刷新缓存/推送） │
└──────────────────────────────────────────────────────────┘
```

### 场景
1. **作者置顶自己的视频评论**：操作成功，原置顶评论（如有）被取消，新评论置顶。
2. **越权置顶**：操作者不是视频作者，抛出业务异常（CODE_600）。
3. **评论不存在或已删除**：查询返回空，抛出业务异常（CODE_600）。

### Mermaid 流程图
```mermaid
graph TD
    A[请求: POST /comment/topComment<br/>commentId] --> B[控制器: CommentController ✅<br/>Mediator.commands]
    B --> C[命令: TopCommentCmd ✅]
    C --> C1{评论存在?}
    C1 -->|否| X[业务异常: 评论不存在 ❌]
    C1 -->|是| C2[校验视频归属 & 权限 ✅]
    C2 --> C3{已有置顶评论?}
    C3 -->|是| C4[取消旧置顶 ✅]
    C3 -->|否| C5[调用 comment.top() ✅]
    C4 --> C5
    C5 --> D[保存事务 ✅]
    D --> E[领域事件: CommentToppedDomainEvent ✅]
    E --> F[事件监听器: CommentToppedDomainEventSubscriber ⚪]
    F --> G[命令: RefreshVideoCommentTopCmd ❌<br/>刷新缓存/推送]
```

---

## 📦 设计元素清单

### ✅ 已存在的设计

- 控制器：`CommentController#commentTop` 使用 Mediator 发送命令（`only-danmuku-adapter/.../CommentController.kt:90`）
- 命令：`TopCommentCmd`、`UntopCommentCmd`（`only-danmuku-application/.../commands/video_comment`）
- 聚合行为：`VideoComment.top()` / `VideoComment.untop()` 自动发布对应领域事件
- 事件监听器：`CommentToppedDomainEventSubscriber`（已生成，待补充缓存刷新逻辑）
- 验证器：`@VideoCommentOwner` 校验评论归属、操作者权限（
  `only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/validater/VideoCommentOwner.kt`）

---

## ❌ 缺失的设计清单

| 类型    | 缺失项                                     | 描述                   | 建议位置                                           | 优先级 |
|-------|-----------------------------------------|----------------------|------------------------------------------------|-----|
| 验证器   | `@CommentTopPermission`                 | 校验置顶操作者是否为视频作者/管理员   | `only-danmuku-application/.../validater/`      | P0  |
| 查询    | `GetTopCommentByVideoQry`               | 获取视频当前置顶评论，供命令内部互斥处理 | `design/aggregate/video_comment/_gen.json`     | P0  |
| 命令    | `RefreshVideoCommentTopCmd`             | 置顶变更后同步缓存/通知         | `design/extra/comment_top_gen.json`            | P1  |
| 事件处理器 | `CommentToppedDomainEventSubscriber` 实现 | 订阅事件后触发刷新命令          | `only-danmuku-application/.../subscribers/...` | P1  |

---

## 🔑 关键业务规则
- **权限**：只有视频作者或具备管理权限的用户可置顶评论。
- **唯一性**：每个视频最多只有一条置顶评论，置顶新评论时需取消旧的。
- **状态维护**：`VideoComment` 中应通过枚举或布尔字段表示置顶状态；取消置顶时恢复为普通状态。
- **展示同步**：置顶变更后应刷新客户端评论列表缓存（事件驱动）。
- **审计**：建议记录置顶操作日志（操作者、目标评论、时间）。

---

## 🧾 控制器与命令示例
```kotlin
@PostMapping("/topComment")
fun commentTop(@RequestBody @Validated request: CommentTop.Request): CommentTop.Response {
    Mediator.commands.send(
        TopCommentCmd.Request(
            commentId = request.commentId.toLong()
        )
    )
    return CommentTop.Response()
}
```

> 参考：`only-danmuku-adapter/src/main/kotlin/edu/only4/danmuku/adapter/portal/api/CommentController.kt`

```kotlin
// 命令核心逻辑（TopCommentCmd.Handler）
val comment = Mediator.repositories.findFirst(
    SVideoComment.predicateById(request.commentId),
    persist = false
).getOrNull() ?: throw KnownException("评论不存在：${request.commentId}")
// TODO: 校验操作者权限、取消旧置顶
comment.top()
Mediator.uow.save()
```

---

## 📂 传统架构参考
- 控制器：`easylive-java/easylive-web/src/main/java/com/easylive/web/controller/VideoCommentController.java:227`
- 相关枚举：`CommentTopTypeEnum`
- 服务实现（需结合 `VideoCommentServiceImpl` 查看具体细节）

---

**文档版本**：v1.1  
**创建时间**：2025-10-22  
**维护者**：开发团队  
**近期变更**：统一为“请求→命令→事件→命令”流程，补充现有命令/事件监听器与缺口说明。

