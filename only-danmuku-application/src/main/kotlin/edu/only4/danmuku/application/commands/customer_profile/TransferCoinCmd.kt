package edu.only4.danmuku.application.commands.customer_profile

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import org.springframework.stereotype.Service

/** 从发送者向接收者转移硬币 */
object TransferCoinCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val sender = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.senderUserId }
            ).get()
            val receiver = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.receiverUserId }
            ).get()

            sender.transferCoin(receiver, request.amount)
            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val senderUserId: Long,
        val receiverUserId: Long,
        val amount: Int,
    ) : RequestParam<Response>

    class Response
}

