package com.edu.only4.danmuku.domain.aggregates.customer_focus.events

import com.edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "CustomerFocus", name = "CustomerFocusUserFocusedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class CustomerFocusUserFocusedDomainEvent(val entity: CustomerFocus)
