package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_message.events.CustomerMessageMessageDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerMessageMessageDeletedDomainEventSubscriber {

    @EventListener(CustomerMessageMessageDeletedDomainEvent::class)
    fun on(event: CustomerMessageMessageDeletedDomainEvent) {

    }
}
