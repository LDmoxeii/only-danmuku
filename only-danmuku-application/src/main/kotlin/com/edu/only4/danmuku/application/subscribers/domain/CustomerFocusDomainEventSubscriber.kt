package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_focus.events.CustomerFocusDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerFocusDomainEventSubscriber {

    @EventListener(CustomerFocusDomainEvent::class)
    fun on(event: CustomerFocusDomainEvent) {

    }
}
