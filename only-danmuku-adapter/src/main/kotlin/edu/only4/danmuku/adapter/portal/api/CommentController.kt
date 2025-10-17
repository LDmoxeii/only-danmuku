package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.video_comment.DelCommentCmd
import edu.only4.danmuku.application.commands.video_comment.PostCommentCmd
import edu.only4.danmuku.application.commands.video_comment.TopCommentCmd
import edu.only4.danmuku.application.commands.video_comment.UntopCommentCmd
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
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
class CommentController {

    /**
     * 加载评论列表(分页)
     */
    @PostMapping("/loadComment")
    fun commentLoad(@RequestBody @Validated request: CommentLoad.Request): PageData<CommentLoad.CommentItem> {
        // 调用查询获取评论分页列表
        val queryRequest = VideoCommentPageQry.Request(
            videoNameFuzzy = null // 前端按视频ID查询，这里不使用模糊查询
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { comment ->
                CommentLoad.CommentItem(
                    commentId = comment.commentId.toString(),
                    pCommentId = null, // TODO: 从评论数据中获取
                    videoId = comment.videoId.toString(),
                    videoUserId = null, // TODO: 从评论数据中获取
                    content = comment.content,
                    imgPath = null, // TODO: 从评论数据中获取
                    userId = comment.customerId.toString(),
                    nickName = comment.customerNickname,
                    avatar = null, // TODO: 从评论数据中获取
                    likeCount = comment.likeCount,
                    haveLike = null, // TODO: 从评论数据中获取
                    topType = comment.topType?.toInt(),
                    postTime = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(comment.postTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    childrenCount = null, // TODO: 从评论数据中获取
                    children = null // TODO: 递归获取子评论
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    /**
     * 发表评论
     */
    @PostMapping("/postComment")
    fun commentPost(@RequestBody @Validated request: CommentPost.Request): CommentPost.Response {
        // 调用命令发表评论
        val commandResult = Mediator.commands.send(
            PostCommentCmd.Request(
                videoId = request.videoId.toLong(),
                replyCommentId = request.replyCommentId?.toLong(),
                content = request.content,
                imgPath = request.imgPath
            )
        )

        // 返回评论对象
        return CommentPost.Response(
            comment = CommentPost.CommentItem(
                commentId = commandResult.commentId.toString(),
                videoId = request.videoId,
                userId = null, // TODO: 从上下文获取当前用户ID
                content = request.content,
                imgPath = request.imgPath,
                postTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        )
    }

    /**
     * 用户删除评论
     */
    @PostMapping("/userDelComment")
    fun commentUserDel(@RequestBody @Validated request: CommentUserDel.Request): CommentUserDel.Response {
        // 调用命令删除评论
        Mediator.commands.send(
            DelCommentCmd.Request(
                commentId = request.commentId.toLong()
            )
        )
        return CommentUserDel.Response()
    }

    /**
     * 置顶评论
     */
    @PostMapping("/topComment")
    fun commentTop(@RequestBody @Validated request: CommentTop.Request): CommentTop.Response {
        // 调用命令置顶评论
        Mediator.commands.send(
            TopCommentCmd.Request(
                commentId = request.commentId.toLong()
            )
        )
        return CommentTop.Response()
    }

    /**
     * 取消置顶评论
     */
    @PostMapping("/cancelTopComment")
    fun commentCancelTop(@RequestBody @Validated request: CommentCancelTop.Request): CommentCancelTop.Response {
        // 调用命令取消置顶评论
        Mediator.commands.send(
            UntopCommentCmd.Request(
                commentId = request.commentId.toLong()
            )
        )
        return CommentCancelTop.Response()
    }

}
