package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.user.events.UserLoginFailedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class UserLoginFailedDomainEventSubscriber {

    @EventListener(UserLoginFailedDomainEvent::class)
    fun on(event: UserLoginFailedDomainEvent) {

    }
}
