package edu.only4.danmuku.application.commands.customer_message

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.customer_message.SCustomerMessage
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType
import org.springframework.stereotype.Service

/**
 * 标记所有消息为已读
 */
object MarkAllAsReadCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val msgTypeEnum = MessageType.valueOfOrNull(request.messageType)

            val messages = Mediator.repositories.find(
                SCustomerMessage.predicate { schema ->
                    schema.allNotNull(
                        schema.customerId eq request.customerId,
                        schema.readType eq ReadType.UNREAD,
                        schema.messageType `eq?` msgTypeEnum
                    )!!
                }
            )

            if (messages.isEmpty()) {
                return
            }

            val now = System.currentTimeMillis() / 1000
            messages.forEach { it.markAsRead(now) }

            Mediator.uow.save()
        }
    }

    data class Request(
        val customerId: Long,
        val messageType: Int? = null,
    ) : RequestParam<Unit>
}
