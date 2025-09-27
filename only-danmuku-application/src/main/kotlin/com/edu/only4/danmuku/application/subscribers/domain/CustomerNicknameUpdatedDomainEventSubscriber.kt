package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerNicknameUpdatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerNicknameUpdatedDomainEventSubscriber {

    @EventListener(CustomerNicknameUpdatedDomainEvent::class)
    fun on(event: CustomerNicknameUpdatedDomainEvent) {

    }
}
