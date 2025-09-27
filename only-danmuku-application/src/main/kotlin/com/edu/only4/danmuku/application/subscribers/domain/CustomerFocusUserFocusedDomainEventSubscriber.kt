package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_focus.events.CustomerFocusUserFocusedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerFocusUserFocusedDomainEventSubscriber {

    @EventListener(CustomerFocusUserFocusedDomainEvent::class)
    fun on(event: CustomerFocusUserFocusedDomainEvent) {

    }
}
