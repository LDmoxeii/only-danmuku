package com.edu.only4.danmuku.domain.aggregates.customer_action.events

import com.edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "CustomerAction", name = "CustomerCoinGivenDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class CustomerCoinGivenDomainEvent(val entity: CustomerAction)
