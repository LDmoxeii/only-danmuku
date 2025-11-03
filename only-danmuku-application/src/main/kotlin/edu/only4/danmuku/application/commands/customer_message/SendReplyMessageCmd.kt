package edu.only4.danmuku.application.commands.customer_message

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import edu.only4.danmuku.domain.aggregates.customer_message.factory.CustomerMessageFactory
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 发送评论回复消息（回复 → 发给被回复评论作者）
 */
object SendReplyMessageCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val parent = Mediator.repositories.findOne(
                SVideoComment.predicateById(request.replyCommentId)
            ).getOrNull() ?: return Response()

            val receiverId = parent.customerId
            if (receiverId == request.sendUserId) {
                return Response()
            }

            val now = System.currentTimeMillis() / 1000
            val extend = UserMessageExtend(
                messageContent = request.content,
                messageContentReply = request.replyCommentContent
            )

            Mediator.factories.create(
                CustomerMessageFactory.Payload(
                    customerId = receiverId,
                    videoId = request.videoId,
                    messageType = MessageType.COMMENT_REPLY,
                    sendSubjectId = request.sendUserId,
                    extendJson = extend,
                    createTime = now,
                )
            )
            Mediator.uow.save()
            return Response()
        }

        // no-op
    }

    data class Request(
        val videoId: Long,
        val sendUserId: Long,
        val content: String?,
        val replyCommentId: Long,
        val replyCommentContent: String?,
    ) : RequestParam<Response>

    class Response
}

