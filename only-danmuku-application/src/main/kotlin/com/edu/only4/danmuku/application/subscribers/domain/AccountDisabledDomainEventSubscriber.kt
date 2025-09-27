package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.user.events.AccountDisabledDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class AccountDisabledDomainEventSubscriber {

    @EventListener(AccountDisabledDomainEvent::class)
    fun on(event: AccountDisabledDomainEvent) {

    }
}
