package com.edu.only4.danmuku.domain.aggregates.customer_video_series

import com.edu.only4.danmuku.domain.aggregates.customer_video_series.factory.CustomerVideoSeriesFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggCustomerVideoSeries (
payload: CustomerVideoSeriesFactory.Payload? = null,
) : Aggregate.Default<CustomerVideoSeries>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerVideoSeries, Long > (key)
}
