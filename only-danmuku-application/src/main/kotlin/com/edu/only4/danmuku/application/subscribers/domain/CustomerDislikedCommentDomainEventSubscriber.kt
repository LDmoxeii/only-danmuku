package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerDislikedCommentDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerDislikedCommentDomainEventSubscriber {

    @EventListener(CustomerDislikedCommentDomainEvent::class)
    fun on(event: CustomerDislikedCommentDomainEvent) {

    }
}
