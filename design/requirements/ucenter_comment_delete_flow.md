# 用户中心评论删除流程设计文档（补充）

> 基于 easylive-java 项目需求，按照 DDD 事件驱动模式设计

## 📋 业务需求概述

UP主在用户中心删除自己视频的评论，或用户删除自己发表的评论。系统验证权限后执行删除操作，与管理后台删除共享同一核心逻辑。

---

## 🔗 关联文档

本文档是 [`comment_delete_flow.md`](./comment_delete_flow.md) 的补充说明，核心删除逻辑请参考主文档。

---

## 📊 与管理后台删除的区别

| 特性 | 管理后台删除 | 用户中心删除 |
|------|-------------|------------|
| **API路径** | `POST /interact/delComment` | `POST /ucenter/delComment` |
| **权限拦截器** | `@GlobalInterceptor` | `@GlobalInterceptor(checkLogin=true)` |
| **传入userId** | `null` (管理员模式) | `tokenUserInfoDto.getUserId()` (用户模式) |
| **权限范围** | 可删除所有评论 | 只能删除自己视频的评论或自己的评论 |
| **使用场景** | 管理员处理违规内容 | UP主管理评论区 / 用户撤回评论 |

---

## 🔑 关键代码差异

### 管理后台删除
```java
// InteractController.java:113
@RequestMapping("/delComment")
public ResponseVO delComment(@NotNull Integer commentId) {
    // 传入 null 表示管理员权限，跳过权限校验
    videoCommentService.deleteComment(commentId, null);
    return getSuccessResponseVO(null);
}
```

### 用户中心删除
```java
// UCenterInteractController.java:107-112
@RequestMapping("/delComment")
@GlobalInterceptor(checkLogin = true)  // 需要登录
public ResponseVO delComment(@NotNull Integer commentId) {
    TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
    // 传入当前用户ID，执行权限校验
    videoCommentService.deleteComment(commentId, tokenUserInfoDto.getUserId());
    return getSuccessResponseVO(null);
}
```

### 核心服务方法（共享）
```java
// VideoCommentServiceImpl.java:321-354
public void deleteComment(Integer commentId, String userId) {
    VideoComment comment = videoCommentMapper.selectByCommentId(commentId);
    if (null == comment) {
        throw new BusinessException(ResponseCodeEnum.CODE_600);
    }

    VideoInfo videoInfo = videoInfoMapper.selectByVideoId(comment.getVideoId());
    if (null == videoInfo) {
        throw new BusinessException(ResponseCodeEnum.CODE_600);
    }

    // 权限校验：只有视频作者或评论作者可以删除
    if (userId != null && !videoInfo.getUserId().equals(userId) && !comment.getUserId().equals(userId)) {
        throw new BusinessException(ResponseCodeEnum.CODE_600);
    }

    // ... 删除逻辑（省略）
}
```

---

## 🎯 权限校验逻辑

### 权限校验代码（行 337-339）
```java
if (userId != null && !videoInfo.getUserId().equals(userId) && !comment.getUserId().equals(userId)) {
    throw new BusinessException(ResponseCodeEnum.CODE_600);
}
```

### 权限判断表

| userId | videoUserId | commentUserId | 结果 |
|--------|-------------|---------------|------|
| `null` | 任意 | 任意 | ✅ 允许（管理员） |
| `U001` | `U001` | 任意 | ✅ 允许（UP主删除自己视频的评论） |
| `U001` | `U002` | `U001` | ✅ 允许（用户删除自己的评论） |
| `U001` | `U002` | `U003` | ❌ 拒绝（无权限） |

---

## 📌 使用场景示例

### 场景 #1: UP主删除自己视频的违规评论

**前端请求**：
```http
POST /ucenter/delComment
Authorization: Bearer {token}
Content-Type: application/json

{
  "commentId": 123456
}
```

**后端处理**：
1. 从Token获取 `userId = "U001"` (UP主)
2. 查询评论 → `comment.videoId = "V999"`
3. 查询视频 → `video.userId = "U001"` (UP主的视频)
4. 权限校验通过：`userId == videoUserId`
5. 执行删除操作

### 场景 #2: 用户删除自己发表的评论

**前端请求**：
```http
POST /ucenter/delComment
Authorization: Bearer {token}
Content-Type: application/json

{
  "commentId": 789012
}
```

**后端处理**：
1. 从Token获取 `userId = "U002"` (评论作者)
2. 查询评论 → `comment.userId = "U002"` (自己的评论)
3. 查询视频 → `video.userId = "U001"` (别人的视频)
4. 权限校验通过：`userId == commentUserId`
5. 执行删除操作

### 场景 #3: 用户试图删除别人的评论（失败）

**前端请求**：
```http
POST /ucenter/delComment
Authorization: Bearer {token}

{
  "commentId": 345678
}
```

**后端处理**：
1. 从Token获取 `userId = "U003"`
2. 查询评论 → `comment.userId = "U002"` (不是自己的评论)
3. 查询视频 → `video.userId = "U001"` (不是自己的视频)
4. 权限校验失败：`userId != videoUserId && userId != commentUserId`
5. 抛出异常：`CODE_600`

---

## 🔄 完整流程（简化）

```
┌─────────────────────────────────────────┐
│ 用户中心：POST /ucenter/delComment      │
│ 需要登录：@GlobalInterceptor(checkLogin=true) │
└────────────────┬────────────────────────┘
                 ↓
┌─────────────────────────────────────────┐
│ 1. 获取当前用户ID from Token            │
│    userId = tokenUserInfoDto.getUserId() │
└────────────────┬────────────────────────┘
                 ↓
┌─────────────────────────────────────────┐
│ 2. 调用共享删除服务                     │
│    deleteComment(commentId, userId)     │
└────────────────┬────────────────────────┘
                 ↓
┌─────────────────────────────────────────┐
│ 3. 权限校验（三级）                     │
│    - userId == null → 管理员 ✅         │
│    - userId == videoUserId → UP主 ✅    │
│    - userId == commentUserId → 作者 ✅  │
│    - 其他 → 无权限 ❌                   │
└────────────────┬────────────────────────┘
                 ↓
         （参考 comment_delete_flow.md）
```

---

## 📝 DDD 设计说明

### 命令复用

用户中心和管理后台使用**同一个命令** `DelCommentCmd`，通过 `operatorId` 参数区分：

```kotlin
data class Request(
    val commentId: Int,
    val operatorId: String?  // null=管理员, 非null=用户
) : RequestParam<Response>
```

### Controller 实现示例

```kotlin
@RestController
@RequestMapping("/ucenter")
class UCenterInteractController {

    @PostMapping("/delComment")
    @GlobalInterceptor(checkLogin = true)
    fun delComment(
        @RequestParam @NotNull commentId: Int
    ): Response {
        val tokenUserInfo = getTokenUserInfoDto()

        // 用户模式：传入当前用户ID
        Mediator.commands.send(
            DelCommentCmd.Request(
                commentId = commentId,
                operatorId = tokenUserInfo.userId  // 用户ID
            )
        )
        return Response()
    }
}
```

---

## 🎯 设计优势

### 1. **代码复用**
- 管理后台和用户中心共享同一删除逻辑
- 通过 `operatorId` 参数实现权限差异化
- 减少代码重复，降低维护成本

### 2. **清晰的权限边界**
- 管理员：无限制（`operatorId = null`）
- UP主：只能管理自己的视频评论区
- 用户：只能删除自己的评论

### 3. **安全性**
- 登录拦截器保证用户身份真实性
- Token验证防止伪造请求
- 权限校验防止越权操作

---

## 🔗 相关文档

- **主文档**: [`comment_delete_flow.md`](./comment_delete_flow.md) - 完整的评论删除流程设计
- **弹幕删除**: [`danmuku_delete_flow.md`](./danmuku_delete_flow.md) - 弹幕删除流程（类似逻辑）

---

**文档版本**：v1.0
**创建时间**：2025-10-22
**维护者**：开发团队
