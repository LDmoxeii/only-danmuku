package edu.only4.danmuku.application.commands.customer_focus

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.NotSelf
import edu.only4.danmuku.application.validator.UserExists
import edu.only4.danmuku.domain._share.meta.customer_focus.SCustomerFocus
import edu.only4.danmuku.domain.aggregates.customer_focus.factory.CustomerFocusFactory
import org.springframework.stereotype.Service

/**
 * 关注
 */
object FocusCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val exists = Mediator.repositories.find(
                SCustomerFocus.predicate { schema ->
                    schema.all(
                        schema.customerId eq request.userId,
                        schema.focusCustomerId eq request.userId
                    )
                }
            ).isNotEmpty()

            if (exists) return


            Mediator.factories.create(
                CustomerFocusFactory.Payload(
                    customerId = request.userId,
                    focusCustomerId = request.focusUserId
                )
            )
            Mediator.uow.save()
        }
    }

    @NotSelf(userIdField = "userId", targetIdField = "focusUserId")
    @UserExists(targetIdField = "focusUserId")
    data class Request(
        val userId: Long,
        val focusUserId: Long,
    ) : RequestParam<Unit>

}
