package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_message.events.CustomerMessageMessagesMarkedAsReadDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerMessageMessagesMarkedAsReadDomainEventSubscriber {

    @EventListener(CustomerMessageMessagesMarkedAsReadDomainEvent::class)
    fun on(event: CustomerMessageMessagesMarkedAsReadDomainEvent) {

    }
}
