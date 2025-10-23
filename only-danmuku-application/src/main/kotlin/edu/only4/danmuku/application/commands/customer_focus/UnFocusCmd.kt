package edu.only4.danmuku.application.commands.customer_focus

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_focus.SCustomerFocus
import edu.only4.danmuku.domain.aggregates.customer_focus.events.UserUnfocusedDomainEvent
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events
import org.springframework.stereotype.Service

/**
 * 取消关注
 */
object UnFocusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val userIdStr = request.userId.toString()
            val focusIdStr = request.focusUserId.toString()

            val toRemove = Mediator.repositories.find(
                SCustomerFocus.predicate { schema ->
                    schema.all(
                        schema.customerId eq userIdStr,
                        schema.focusCustomerId eq focusIdStr
                    )
                },
                persist = false
            )

            toRemove.forEach { entity ->
                Mediator.uow.remove(entity)
            }

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val userId: Long,
        val focusUserId: Long,
    ) : RequestParam<Response>

    class Response
}
