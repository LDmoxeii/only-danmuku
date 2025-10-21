package edu.only4.danmuku.application.commands.customer_focus

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_focus.SCustomerFocus
import edu.only4.danmuku.domain.aggregates.customer_focus.factory.CustomerFocusFactory
import org.springframework.stereotype.Service

/**
 * 关注
 */
object FocusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            if (request.userId == request.focusUserId) {
                throw KnownException("不能关注自己")
            }

            val userIdStr = request.userId.toString()
            val focusIdStr = request.focusUserId.toString()

            val exists = Mediator.repositories.find(
                SCustomerFocus.predicate { schema ->
                    schema.all(
                        schema.customerId eq userIdStr,
                        schema.focusCustomerId eq focusIdStr
                    )
                }
            ).isNotEmpty()

            if (!exists) {
                Mediator.factories.create(
                    CustomerFocusFactory.Payload(
                        customerId = userIdStr,
                        focusCustomerId = focusIdStr
                    )
                )
                Mediator.uow.save()
            }
            return Response()
        }
    }

    data class Request(
        val userId: Long,
        val focusUserId: Long,
    ) : RequestParam<Response>

    class Response
}
