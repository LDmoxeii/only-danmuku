package edu.only4.danmuku.application.commands.customer_focus

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_focus.SCustomerFocus
import edu.only4.danmuku.domain.aggregates.customer_focus.factory.CustomerFocusFactory
import edu.only4.danmuku.application.validator.NotSelf
import edu.only4.danmuku.application.validator.UserExists
import org.springframework.stereotype.Service

/**
 * 关注
 */
object FocusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val exists = Mediator.repositories.find(
                SCustomerFocus.predicate { schema ->
                    schema.all(
                        schema.customerId eq request.userId,
                        schema.focusCustomerId eq request.userId
                    )
                }
            ).isNotEmpty()

            if (exists) return Response()


            Mediator.factories.create(
                CustomerFocusFactory.Payload(
                    customerId = request.userId,
                    focusCustomerId = request.focusUserId
                )
            )
            Mediator.uow.save()

            return Response()
        }
    }

    @NotSelf(userIdField = "userId", targetIdField = "focusUserId")
    @UserExists(targetIdField = "focusUserId")
    data class Request(
        val userId: Long,
        val focusUserId: Long,
    ) : RequestParam<Response>

    class Response
}
