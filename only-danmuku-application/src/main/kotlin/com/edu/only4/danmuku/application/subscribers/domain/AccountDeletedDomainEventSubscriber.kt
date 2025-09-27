package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.user.events.AccountDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class AccountDeletedDomainEventSubscriber {

    @EventListener(AccountDeletedDomainEvent::class)
    fun on(event: AccountDeletedDomainEvent) {

    }
}
