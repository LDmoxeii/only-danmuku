package com.edu.only4.danmuku.domain.aggregates.statistics.factory

import com.edu.only4.danmuku.domain.aggregates.statistics.Statistics
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "Statistics", name = "StatisticsFactory", type = Aggregate.TYPE_FACTORY, description = "")
class StatisticsFactory : AggregateFactory<StatisticsFactory.Payload, Statistics> {
    override fun create(entityPayload: Payload): Statistics {
        return Statistics(

        )
    }

    @Aggregate(aggregate = "Statistics", name = "StatisticsPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<Statistics>
}
