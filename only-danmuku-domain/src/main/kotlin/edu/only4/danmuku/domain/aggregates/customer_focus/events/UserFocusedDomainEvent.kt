package edu.only4.danmuku.domain.aggregates.customer_focus.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus

@DomainEvent(persist = false)
@Aggregate(
    aggregate = "CustomerFocus",
    name = "UserFocusedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = "用户已关注"
)
class UserFocusedDomainEvent(val entity: CustomerFocus)

