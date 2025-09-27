package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.user.events.PasswordResetDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class PasswordResetDomainEventSubscriber {

    @EventListener(PasswordResetDomainEvent::class)
    fun on(event: PasswordResetDomainEvent) {

    }
}
