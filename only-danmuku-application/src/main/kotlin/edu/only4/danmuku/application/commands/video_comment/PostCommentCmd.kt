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
                    replyCustomerId = request.replyCustomerId,
                    postTime = now
                )
            )

            Mediator.uow.save()

            return Response(commentId = comment.id)
        }
    }

    @ReplyCommentExists(videoIdField = "videoId", replyCommentIdField = "replyCommentId")
    data class Request(
        @field:VideoExists
        @field:CommentNotClosed
        val videoId: Long,
        val replyCommentId: Long? = null,
        val customerId: Long,
        val replyCustomerId: Long,
        val content: String,
        val imgPath: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val commentId: Long
    )
}
