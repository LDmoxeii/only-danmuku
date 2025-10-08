package com.edu.only4.danmuku.domain.aggregates.customer_message.factory

import com.edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "CustomerMessage", name = "CustomerMessageFactory", type = Aggregate.TYPE_FACTORY, description = "")
class CustomerMessageFactory : AggregateFactory<CustomerMessageFactory.Payload, CustomerMessage> {
    override fun create(entityPayload: Payload): CustomerMessage {
        return CustomerMessage(

        )
    }

    @Aggregate(aggregate = "CustomerMessage", name = "CustomerMessagePayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<CustomerMessage>
}
