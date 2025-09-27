package com.edu.only4.danmuku.domain.aggregates.statistics

import com.edu.only4.danmuku.domain.aggregates.statistics.factory.StatisticsFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggStatistics (
payload: StatisticsFactory.Payload? = null,
) : Aggregate.Default<Statistics>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggStatistics, Long > (key)
}
