package com.edu.only4.danmuku.domain.aggregates.customer_message.events

import com.edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "CustomerMessage", name = "CustomerMessageMessageSentDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class CustomerMessageMessageSentDomainEvent(val entity: CustomerMessage)
