package edu.only4.danmuku.application.commands.customer_message

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_message.SCustomerMessage
import org.springframework.stereotype.Service

/**
 * 删除消息
 */
object DeleteMessageCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 仅删除当前用户自己的指定消息（软删）
            Mediator.repositories.remove(
                SCustomerMessage.predicate { schema ->
                    schema.all(
                        schema.id eq request.messageId,
                        schema.customerId eq request.customerId,
                    )
                }
            )

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 用户ID */
        val customerId: Long,
        /** 消息ID */
        val messageId: Long,
    ) : RequestParam<Response>

    class Response
}
