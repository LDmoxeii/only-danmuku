package edu.only4.danmuku.domain.aggregates.customer_video_series

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_video_series.factory.CustomerVideoSeriesFactory

/**
 * CustomerVideoSeries聚合封装
 * 用户视频序列归档;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggCustomerVideoSeries(
    payload: CustomerVideoSeriesFactory.Payload? = null,
) : Aggregate.Default<CustomerVideoSeries>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerVideoSeries, Long>(key)

}
