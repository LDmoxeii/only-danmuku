package edu.only4.danmuku.application.commands.customer_message

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_message.SCustomerMessage
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.customer_message.factory.CustomerMessageFactory
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 发送点赞消息
 */
object SendLikeMessageCmd {

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

            val exists = Mediator.repositories.findFirst(
                SCustomerMessage.predicate { s ->
                    s.all(
                        s.customerId eq receiverId,
                        s.sendSubjectId eq request.sendUserId,
                        s.videoId eq request.videoId,
                        s.messageType eq MessageType.OTHER_MESSAGE,
                    )
                }
            ).isPresent

            if (!exists) {
                val now = System.currentTimeMillis() / 1000
                Mediator.factories.create(
                    CustomerMessageFactory.Payload(
                        customerId = receiverId,
                        videoId = request.videoId,
                        messageType = MessageType.OTHER_MESSAGE,
                        sendSubjectId = request.sendUserId,
                        extendJson = """{"action":"LIKE"}""",
                        createTime = now,
                    )
                )
                Mediator.uow.save()
            }

            return Response()
        }

    }

    data class Request(
        val videoId: Long,
        val sendUserId: Long,
    ) : RequestParam<Response>

    class Response
}
