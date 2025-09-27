package com.edu.only4.danmuku.domain.aggregates.statistics_info.factory

import com.edu.only4.danmuku.domain.aggregates.statistics_info.StatisticsInfo
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "StatisticsInfo", name = "StatisticsInfoFactory", type = Aggregate.TYPE_FACTORY, description = "")
class StatisticsInfoFactory : AggregateFactory<StatisticsInfoFactory.Payload, StatisticsInfo> {
    override fun create(payload: Payload): StatisticsInfo {
        return StatisticsInfo(

        )
    }

    @Aggregate(aggregate = "StatisticsInfo", name = "StatisticsInfoPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<StatisticsInfo>
}
