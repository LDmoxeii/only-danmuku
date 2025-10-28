package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.video_comment.DelCommentCmd
import edu.only4.danmuku.application.commands.video_comment.PostCommentCmd
import edu.only4.danmuku.application.commands.video_comment.TopCommentCmd
import edu.only4.danmuku.application.commands.video_comment.UntopCommentCmd
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import edu.only4.danmuku.application.queries.customer_action.GetUserActionsByVideoIdQry
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 视频评论控制器
 */
@RestController
@RequestMapping("/comment")
@Validated
class CompatibleVideoCommentController {

    /**
     * 加载评论列表(分页)
     */
    @PostMapping("/loadComment")
    fun commentLoad(request: CommentLoad.Request): CommentLoad.Result {

        // 调用查询获取评论分页列表
        val queryRequest = VideoCommentPageQry.Request(
            videoId = request.videoId.toLong(),
            videoNameFuzzy = null
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 当前登录用户
        val currentUserId = LoginHelper.getUserId()

        // 加载用户在该视频下的行为列表，用于标记是否点赞过评论
        val actionList = if (currentUserId != null) {
            Mediator.queries.send(
                GetUserActionsByVideoIdQry.Request(
                    userId = currentUserId,
                    videoId = request.videoId.toLong()
                )
            )
        } else emptyList()

        // 构建已点赞的评论ID集合
        val likedCommentIds: Set<Long> = actionList
            .filter { it.actionType == ActionType.LIKE_COMMENT.code && it.commentId != null }
            .mapNotNull { it.commentId }
            .toSet()

        // 转换为前端需要的格式
        val pageData = PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { comment ->
                toCommentItem(comment, likedCommentIds)
            },
            totalCount = queryResult.totalCount
        )

        // 映射用户行为列表返回（时间格式与视频详情一致）
        val userActions = actionList.map { act ->
            CommentLoad.UserAction(
                actionId = act.actionId,
                userId = act.userId,
                videoId = act.videoId,
                videoName = act.videoName,
                videoCover = act.videoCover,
                videoUserId = act.videoUserId,
                commentId = act.commentId,
                actionType = act.actionType,
                actionCount = act.actionCount,
                cationTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(act.actionTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        }

        return CommentLoad.Result(
            commentData = pageData,
            userActionList = userActions
        )
    }

    /**
     * 递归转换评论响应为前端格式
     */
    private fun toCommentItem(comment: VideoCommentPageQry.Response, likedCommentIds: Set<Long>): CommentLoad.Response {
        return CommentLoad.Response(
            commentId = comment.commentId.toString(),
            pCommentId = comment.parentCommentId.toString(),
            videoId = comment.videoId.toString(),
            videoUserId = comment.videoUserId.toString(),
            videoName = comment.videoName,
            videoCover = comment.videoCover,
            content = comment.content,
            imgPath = comment.imgPath,
            userId = comment.customerId.toString(),
            nickName = comment.customerNickname,
            avatar = comment.customerAvatar,
            likeCount = comment.likeCount,
            hateCount = comment.hateCount,
            topType = comment.topType,
            haveLike = if (likedCommentIds.contains(comment.commentId)) 1 else 0,
            replyUserId = comment.replyCustomerId?.toString(),
            replyNickName = comment.replyCustomerNickname,
            replyAvatar = null,
            postTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(comment.postTime),
                ZoneId.systemDefault()
            ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            childrenCount = comment.childrenCount,
            children = comment.children?.map { child -> toCommentItem(child, likedCommentIds) }
        )
    }

    /**
     * 发表评论
     */
    @PostMapping("/postComment")
    fun commentPost(
        videoId: Long,
        replyCommentId: Long?,
        @NotEmpty @Size(max = 500) content: String,
        @Size(max = 50) imgPath: String?,
    ): CommentPost.Response {
        // 调用命令发表评论
        val currentUserId = LoginHelper.getUserId()!!
        // TODO： 是否回复顶级评论

        Mediator.commands.send(
            PostCommentCmd.Request(
                videoId = videoId,
                replyCommentId = replyCommentId,
                customerId = currentUserId,
                content = content,
                imgPath = imgPath
            )
        )

        // 返回评论对象
        return CommentPost.Response()
    }

    /**
     * 用户删除评论
     */
    @PostMapping("/userDelComment")
    fun commentUserDel(commentId: Long): CommentUserDel.Response {
        // 调用命令删除评论
        Mediator.commands.send(
            DelCommentCmd.Request(
                commentId = commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
        return CommentUserDel.Response()
    }

    /**
     * 置顶评论
     */
    @PostMapping("/topComment")
    fun commentTop(commentId: Long): CommentTop.Response {
        // 调用命令置顶评论
        Mediator.commands.send(
            TopCommentCmd.Request(
                commentId = commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
        return CommentTop.Response()
    }

    /**
     * 取消置顶评论
     */
    @PostMapping("/cancelTopComment")
    fun commentCancelTop(commentId: Long): CommentCancelTop.Response {
        Mediator.commands.send(
            UntopCommentCmd.Request(
                commentId = commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
        return CommentCancelTop.Response()
    }

}
