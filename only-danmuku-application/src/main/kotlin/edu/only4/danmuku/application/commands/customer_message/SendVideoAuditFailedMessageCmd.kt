package edu.only4.danmuku.application.commands.customer_message

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend
import edu.only4.danmuku.domain.aggregates.customer_message.factory.CustomerMessageFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 发送视频审核不通过系统消息
 */
object SendVideoAuditFailedMessageCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoId)
            ).getOrNull() ?: return Response()

            val extend = UserMessageExtend(auditStatus = 5)
            Mediator.factories.create(
                CustomerMessageFactory.Payload(
                    customerId = video.customerId,
                    videoId = request.videoId,
                    messageType = MessageType.SYSTEM_MESSAGE,
                    sendSubjectId = request.operatorId,
                    extendJson = extend,
                )
            )
            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val videoId: Long,
        val operatorId: Long? = null,
        val messageContent: String? = null,
    ) : RequestParam<Response>

    class Response
}
