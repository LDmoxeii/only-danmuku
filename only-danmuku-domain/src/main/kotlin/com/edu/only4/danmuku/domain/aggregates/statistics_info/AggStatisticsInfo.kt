package com.edu.only4.danmuku.domain.aggregates.statistics_info

import com.edu.only4.danmuku.domain.aggregates.statistics_info.factory.StatisticsInfoFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggStatisticsInfo (
payload: StatisticsInfoFactory.Payload? = null,
) : Aggregate.Default<StatisticsInfo>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggStatisticsInfo, Long > (key)
}
