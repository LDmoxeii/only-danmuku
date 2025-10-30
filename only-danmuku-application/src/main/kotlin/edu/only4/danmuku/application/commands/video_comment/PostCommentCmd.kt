package edu.only4.danmuku.application.commands.video_comment

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.CommentNotClosed
import edu.only4.danmuku.application.validator.ReplyCommentExists
import edu.only4.danmuku.application.validator.VideoExists
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.video_comment.factory.VideoCommentFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 发表评论
 */
object PostCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 查找视频，获取视频作者ID
            val video = Mediator.repositories.findOne(
                SVideo.predicateById(request.videoId),
                persist = false
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            // TODO： 是否回复顶级评论
            // 判断是否是回复其他评论的情况
            // 如果被回复的是顶级评论，则将当前评论设为该顶级评论的子评论
            // 如果被回复的是子评论，则继承其父评论ID，并设置被回复用户信息
            // 如果不是回复其他评论，则设置为顶级评论（pCommentId=0）
            val parentId = request.replyCommentId ?: 0L
            val now = System.currentTimeMillis() / 1000

            val comment = Mediator.factories.create(
                VideoCommentFactory.Payload(
                    parentId = parentId,
                    videoId = request.videoId,
                    videoOwnerId = video.customerId,
                    content = request.content,
                    imgPath = request.imgPath,
                    customerId = request.customerId,
                    replyCustomerId = null,
                    postTime = now
                )
            )

            Mediator.uow.save()

            return Response(commentId = comment.id)
        }
    }

    @ReplyCommentExists(videoIdField = "videoId", replyCommentIdField = "replyCommentId")
    data class Request(
        /** 视频ID */
        @field:VideoExists
        @field:CommentNotClosed
        val videoId: Long,
        /** 回复评论ID */
        val replyCommentId: Long? = null,
        /** 发评论的用户ID */
        val customerId: Long,
        /** 评论内容 */
        val content: String,
        /** 图片路径 */
        val imgPath: String? = null,
    ) : RequestParam<Response>

    data class Response(
        /** 评论ID */
        val commentId: Long
    )
}
