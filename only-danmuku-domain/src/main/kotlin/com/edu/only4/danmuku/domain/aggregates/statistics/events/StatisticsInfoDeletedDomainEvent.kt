package com.edu.only4.danmuku.domain.aggregates.statistics.events

import com.edu.only4.danmuku.domain.aggregates.statistics.Statistics
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "Statistics", name = "StatisticsInfoDeletedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class StatisticsInfoDeletedDomainEvent(val entity: Statistics)
