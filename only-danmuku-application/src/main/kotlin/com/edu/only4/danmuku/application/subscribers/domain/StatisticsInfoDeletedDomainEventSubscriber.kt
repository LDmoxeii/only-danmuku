package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.statistics.events.StatisticsInfoDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class StatisticsInfoDeletedDomainEventSubscriber {

    @EventListener(StatisticsInfoDeletedDomainEvent::class)
    fun on(event: StatisticsInfoDeletedDomainEvent) {

    }
}
