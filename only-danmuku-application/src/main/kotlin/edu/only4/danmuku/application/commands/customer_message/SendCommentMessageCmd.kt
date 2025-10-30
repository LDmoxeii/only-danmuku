package edu.only4.danmuku.application.commands.customer_message

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import edu.only4.danmuku.domain.aggregates.customer_message.factory.CustomerMessageFactory
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 发送评论/回复消息
 */
object SendCommentMessageCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 确定接收人：优先回复对象，其次视频作者
            val receiverId: Long? = if (request.replyCommentId != null) {
                val reply = Mediator.repositories.findOne(
                    SVideoComment.predicateById(request.replyCommentId!!)
                ).getOrNull()
                reply?.customerId
            } else {
                val video = Mediator.repositories.findOne(
                    SVideo.predicateById(request.videoId)
                ).getOrNull()
                video?.customerId
            }

            if (receiverId == null || receiverId == request.sendUserId) {
                return Response()
            }

            val now = System.currentTimeMillis() / 1000
            val extend = buildString {
                append("{")
                append("\"messageContent\":")
                append('"').append((request.content ?: "").replace("\"", "\\\"")).append('"')
                if (request.replyCommentContent != null) {
                    append(',')
                    append("\"messageContentReply\":")
                    append('"').append(request.replyCommentContent.replace("\"", "\\\"")).append('"')
                }
                append("}")
            }

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

    }

    data class Request(
        val videoId: Long,
        val sendUserId: Long,
        val content: String?,
        val replyCommentId: Long? = null,
        val replyCommentContent: String? = null,
    ) : RequestParam<Response>

    class Response
}
