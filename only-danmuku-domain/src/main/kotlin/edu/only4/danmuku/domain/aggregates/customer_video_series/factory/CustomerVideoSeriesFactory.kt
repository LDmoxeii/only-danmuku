package edu.only4.danmuku.domain.aggregates.customer_video_series.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeries

import org.springframework.stereotype.Service

/**
 * 用户视频序列归档;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "CustomerVideoSeries",
    name = "CustomerVideoSeriesFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class CustomerVideoSeriesFactory : AggregateFactory<CustomerVideoSeriesFactory.Payload, CustomerVideoSeries> {

    override fun create(payload: Payload): CustomerVideoSeries {
        return CustomerVideoSeries(

        )
    }

     @Aggregate(
        aggregate = "CustomerVideoSeries",
        name = "CustomerVideoSeriesPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<CustomerVideoSeries>

}
