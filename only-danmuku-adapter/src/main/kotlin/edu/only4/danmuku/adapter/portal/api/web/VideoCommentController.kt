package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.video_comment.CancelTopComment
import edu.only4.danmuku.adapter.portal.api.payload.video_comment.DeleteComment
import edu.only4.danmuku.adapter.portal.api.payload.video_comment.PostComment
import edu.only4.danmuku.adapter.portal.api.payload.video_comment.GetCommentPage
import edu.only4.danmuku.adapter.portal.api.payload.video_comment.TopComment
import edu.only4.danmuku.application.commands.video_comment.DeleteVideoCommentCmd
import edu.only4.danmuku.application.commands.video_comment.PostCommentCmd
import edu.only4.danmuku.application.commands.video_comment.TopCommentCmd
import edu.only4.danmuku.application.commands.video_comment.UntopCommentCmd
import edu.only4.danmuku.application.queries.customer_action.GetUserActionsByVideoIdQry
import edu.only4.danmuku.application.queries.video_comment.GetCommentByIdQry
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment")
class VideoCommentController {

    @SaIgnore
    @PostMapping("/page")
    fun page(@RequestBody @Validated request: GetCommentPage.Request): GetCommentPage.Result {
        val queryRequest = VideoCommentPageQry.Request(
            videoId = request.videoId,
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        val currentUserId = LoginHelper.getUserId()

        val actionList = if (currentUserId != null) {
            Mediator.queries.send(
                GetUserActionsByVideoIdQry.Request(
                    userId = currentUserId,
                    videoId = request.videoId
                )
            )
        } else emptyList()

        val likedCommentIds: Set<Long> = actionList
            .filter { it.actionType == ActionType.LIKE_COMMENT && it.commentId != null }
            .mapNotNull { it.commentId }
            .toSet()

        val pageData = PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { comment ->
                coverToCommentItem(comment, likedCommentIds)
            },
            totalCount = queryResult.totalCount
        )

        val userActions = actionList.map { act ->
            GetCommentPage.UserAction(
                actionId = act.actionId,
                userId = act.userId,
                videoId = act.videoId,
                videoName = act.videoName,
                videoCover = act.videoCover,
                videoUserId = act.videoUserId,
                commentId = act.commentId,
                actionType = act.actionType,
                actionCount = act.actionCount,
                cationTime = act.actionTime,
            )
        }

        return GetCommentPage.Result(
            commentData = pageData,
            userActionList = userActions
        )
    }

    private fun coverToCommentItem(
        comment: VideoCommentPageQry.Response,
        likedCommentIds: Set<Long>
    ): GetCommentPage.Response {
        return GetCommentPage.Response(
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
            postTime = comment.postTime,
            childrenCount = comment.childrenCount,
            children = comment.children?.map { child -> coverToCommentItem(child, likedCommentIds) }
        )
    }

    @PostMapping("/post")
    fun post(@RequestBody @Validated request: PostComment.Request) {
        // 调用命令发表评论
        val currentUserId = LoginHelper.getUserId()!!
        var replyCustomerId = 0L
        request.replyCommentId?.let {
            replyCustomerId = Mediator.queries.send(
                GetCommentByIdQry.Request(
                    commentId = request.replyCommentId
                )
            ).userId
        }

        Mediator.commands.send(
            PostCommentCmd.Request(
                videoId = request.videoId,
                replyCommentId = request.replyCommentId,
                customerId = currentUserId,
                replyCustomerId = replyCustomerId,
                content = request.content,
                imgPath = request.imgPath
            )
        )
    }

    @PostMapping("/delete")
    fun delete(@RequestBody @Validated request: DeleteComment.Request) {
        // 调用命令删除评论
        Mediator.commands.send(
            DeleteVideoCommentCmd.Request(
                commentId = request.commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
    }

    @PostMapping("/top")
    fun top(@RequestBody @Validated request: TopComment.Request) {
        Mediator.commands.send(
            TopCommentCmd.Request(
                commentId = request.commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
    }

    @PostMapping("/cancelTop")
    fun cancelTop(@RequestBody @Validated request: CancelTopComment.Request) {
        Mediator.commands.send(
            UntopCommentCmd.Request(
                commentId = request.commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
    }

}
