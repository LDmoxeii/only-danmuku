package edu.only4.danmuku.domain.aggregates.statistics

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.statistics.factory.StatisticsFactory

/**
 * Statistics聚合封装
 * 统计信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggStatistics(
    payload: StatisticsFactory.Payload? = null,
) : Aggregate.Default<Statistics>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggStatistics, Long>(key)

}
