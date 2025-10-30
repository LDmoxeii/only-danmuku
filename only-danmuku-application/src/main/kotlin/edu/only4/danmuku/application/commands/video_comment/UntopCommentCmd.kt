package edu.only4.danmuku.application.commands.video_comment

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.VideoCommentOwner
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 取消置顶评论
 */
object UntopCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val comment = Mediator.repositories.findOne(
                SVideoComment.predicateById(request.commentId),
            ).getOrNull() ?: throw KnownException("评论不存在：${request.commentId}")

            comment.untop()

            Mediator.uow.save()
            return Response()
        }
    }

    @VideoCommentOwner
    data class Request(
        /** 评论ID */
        val commentId: Long,
        /** 操作者ID */
        val operatorId: Long
    ) : RequestParam<Response>

    class Response
}
