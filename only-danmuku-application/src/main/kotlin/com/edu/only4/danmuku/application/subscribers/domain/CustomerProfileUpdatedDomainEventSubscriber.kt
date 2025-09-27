package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileUpdatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerProfileUpdatedDomainEventSubscriber {

    @EventListener(CustomerProfileUpdatedDomainEvent::class)
    fun on(event: CustomerProfileUpdatedDomainEvent) {

    }
}
