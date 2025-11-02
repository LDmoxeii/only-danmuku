package edu.only4.danmuku.application.commands.customer_message

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.customer_message.factory.CustomerMessageFactory
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 发送评论消息（顶级评论 → 发给视频作者）
 */
object SendCommentMessageCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findOne(
                SVideo.predicateById(request.videoId)
            ).getOrNull() ?: return Response()

            val receiverId = video.customerId
            if (receiverId == request.sendUserId) {
                return Response()
            }

            val now = System.currentTimeMillis() / 1000
            val msgContent = escapeJson(request.content)
            val extend = """{"messageContent":$msgContent}"""

            Mediator.factories.create(
                CustomerMessageFactory.Payload(
                    customerId = receiverId,
                    videoId = request.videoId,
                    messageType = MessageType.COMMENT_MENTION,
                    sendSubjectId = request.sendUserId,
                    extendJson = extend,
                    createTime = now,
                )
            )
            Mediator.uow.save()
            return Response()
        }

        private fun escapeJson(content: String?): String {
            val raw = content ?: ""
            val esc = raw.replace("\\", "\\\\").replace("\"", "\\\"")
            return "\"$esc\""
        }
    }

    data class Request(
        val videoId: Long,
        val sendUserId: Long,
        val content: String?,
    ) : RequestParam<Response>

    class Response
}
