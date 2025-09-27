package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.statistics.events.StatisticsInfoAddedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class StatisticsInfoAddedDomainEventSubscriber {

    @EventListener(StatisticsInfoAddedDomainEvent::class)
    fun on(event: StatisticsInfoAddedDomainEvent) {

    }
}
