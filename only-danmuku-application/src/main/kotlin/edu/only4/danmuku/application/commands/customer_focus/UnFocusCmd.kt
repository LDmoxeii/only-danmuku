package edu.only4.danmuku.application.commands.customer_focus

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.customer_focus.SCustomerFocus
import org.springframework.stereotype.Service

/**
 * 取消关注
 */
object UnFocusCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val userIdStr = request.userId.toString()
            val focusIdStr = request.focusUserId.toString()

            Mediator.repositories.remove(
                SCustomerFocus.predicate { schema ->
                    schema.all(
                        schema.customerId eq userIdStr,
                        schema.focusCustomerId eq focusIdStr
                    )
                }
            )

            Mediator.uow.save()
        }
    }

    data class Request(
        val userId: Long,
        val focusUserId: Long,
    ) : RequestParam<Unit>
}
