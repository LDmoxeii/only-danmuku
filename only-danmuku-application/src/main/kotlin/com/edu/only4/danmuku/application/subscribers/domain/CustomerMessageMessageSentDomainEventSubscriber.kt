package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_message.events.CustomerMessageMessageSentDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerMessageMessageSentDomainEventSubscriber {

    @EventListener(CustomerMessageMessageSentDomainEvent::class)
    fun on(event: CustomerMessageMessageSentDomainEvent) {

    }
}
