package com.edu.only4.danmuku.domain.aggregates.customer_profile.events

import com.edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "CustomerProfile", name = "CustomerProfileCreatedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class CustomerProfileCreatedDomainEvent(val entity: CustomerProfile)
