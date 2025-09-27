package com.edu.only4.danmuku.domain.aggregates.customer_focus.factory

import com.edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "CustomerFocus", name = "CustomerFocusFactory", type = Aggregate.TYPE_FACTORY, description = "")
class CustomerFocusFactory : AggregateFactory<CustomerFocusFactory.Payload, CustomerFocus> {
    override fun create(payload: Payload): CustomerFocus {
        return CustomerFocus(

        )
    }

    @Aggregate(aggregate = "CustomerFocus", name = "CustomerFocusPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<CustomerFocus>
}
