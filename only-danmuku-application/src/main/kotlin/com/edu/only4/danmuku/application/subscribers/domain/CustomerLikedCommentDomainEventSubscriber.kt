package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerLikedCommentDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CustomerLikedCommentDomainEventSubscriber {

    @EventListener(CustomerLikedCommentDomainEvent::class)
    fun on(event: CustomerLikedCommentDomainEvent) {

    }
}
