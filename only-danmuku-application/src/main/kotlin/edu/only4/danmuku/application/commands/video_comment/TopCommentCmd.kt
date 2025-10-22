package edu.only4.danmuku.application.commands.video_comment

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.CommentExists
import edu.only4.danmuku.application.validater.VideoCommentOwner
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 置顶评论
 */
object TopCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val comment = Mediator.repositories.findFirst(
                SVideoComment.predicateById(request.commentId)
            ).getOrNull() ?: throw KnownException("评论不存在：${request.commentId}")

            val existingTopComments = Mediator.repositories.find(
                SVideoComment.predicate { schema ->
                    schema.all(
                        schema.videoId eq comment.videoId,
                        schema.topType eq 1.toByte(),
                        schema.id neq comment.id
                    )
                }
            )

            existingTopComments.forEach { it.untop() }
            comment.top()
            Mediator.uow.save()
            return Response()
        }
    }

    @VideoCommentOwner
    data class Request(
        /** 评论ID */
        @field:CommentExists
        val commentId: Long,
        /** 操作用户ID，null 表示后台管理员 */
        val operatorId: Long? = null,
    ) : RequestParam<Response>

    class Response
}
