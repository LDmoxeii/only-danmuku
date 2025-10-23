package edu.only4.danmuku.domain.aggregates.statistics.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.statistics.Statistics
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType

import org.springframework.stereotype.Service

/**
 * 统计信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "Statistics",
    name = "StatisticsFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class StatisticsFactory : AggregateFactory<StatisticsFactory.Payload, Statistics> {

    override fun create(payload: Payload): Statistics {
        return Statistics(
            customerId = payload.customerId,
            dataType = payload.dataType,
            statisticsCount = payload.statisticsCount,
            statisticsDate = payload.statisticsDate
        )
    }

     @Aggregate(
        aggregate = "Statistics",
        name = "StatisticsPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val customerId: Long,
        val dataType: StatisticsDataType,
        val statisticsCount: Int,
        val statisticsDate: Long
    ) : AggregatePayload<Statistics>

}
