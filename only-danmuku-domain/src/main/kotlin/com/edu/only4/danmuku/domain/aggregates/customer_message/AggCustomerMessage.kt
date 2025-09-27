package com.edu.only4.danmuku.domain.aggregates.customer_message

import com.edu.only4.danmuku.domain.aggregates.customer_message.factory.CustomerMessageFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggCustomerMessage (
payload: CustomerMessageFactory.Payload? = null,
) : Aggregate.Default<CustomerMessage>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerMessage, Long > (key)
}
