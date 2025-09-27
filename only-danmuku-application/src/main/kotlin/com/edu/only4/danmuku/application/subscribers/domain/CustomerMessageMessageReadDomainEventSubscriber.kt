package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_message.events.CustomerMessageMessageReadDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerMessageMessageReadDomainEventSubscriber {

    @EventListener(CustomerMessageMessageReadDomainEvent::class)
    fun on(event: CustomerMessageMessageReadDomainEvent) {

    }
}
