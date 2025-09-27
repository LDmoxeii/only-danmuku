package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.category.events.CategoryInfoUpdatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CategoryInfoUpdatedDomainEventSubscriber {

    @EventListener(CategoryInfoUpdatedDomainEvent::class)
    fun on(event: CategoryInfoUpdatedDomainEvent) {

    }
}
