package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.category.events.CategorySortOrderUpdatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CategorySortOrderUpdatedDomainEventSubscriber {

    @EventListener(CategorySortOrderUpdatedDomainEvent::class)
    fun on(event: CategorySortOrderUpdatedDomainEvent) {

    }
}
