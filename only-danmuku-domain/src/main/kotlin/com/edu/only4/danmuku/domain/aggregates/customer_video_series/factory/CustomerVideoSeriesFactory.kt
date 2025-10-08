package com.edu.only4.danmuku.domain.aggregates.customer_video_series.factory

import com.edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeries
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "CustomerVideoSeries", name = "CustomerVideoSeriesFactory", type = Aggregate.TYPE_FACTORY, description = "")
class CustomerVideoSeriesFactory : AggregateFactory<CustomerVideoSeriesFactory.Payload, CustomerVideoSeries> {
    override fun create(entityPayload: Payload): $ {
        Entity
    }

    {
        return CustomerVideoSeries(

        )
    }

    @Aggregate(aggregate = "CustomerVideoSeries", name = "CustomerVideoSeriesPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<CustomerVideoSeries>
}
