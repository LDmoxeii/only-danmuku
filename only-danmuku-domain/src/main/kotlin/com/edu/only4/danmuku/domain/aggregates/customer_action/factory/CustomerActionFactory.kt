package com.edu.only4.danmuku.domain.aggregates.customer_action.factory

import com.edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "CustomerAction", name = "CustomerActionFactory", type = Aggregate.TYPE_FACTORY, description = "")
class CustomerActionFactory : AggregateFactory<CustomerActionFactory.Payload, CustomerAction> {
    override fun create(entityPayload: Payload): $ {
        Entity
    }

    {
        return CustomerAction(

        )
    }

    @Aggregate(aggregate = "CustomerAction", name = "CustomerActionPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<CustomerAction>
}
