package com.edu.only4.danmuku.domain.aggregates.user.events

import com.edu.only4.danmuku.domain.aggregates.user.User
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "User", name = "PasswordChangedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class PasswordChangedDomainEvent(val entity: User)
