package com.edu.only4.danmuku.domain.aggregates.customer_action

import com.edu.only4.danmuku.domain.aggregates.customer_action.factory.CustomerActionFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggCustomerAction (
payload: CustomerActionFactory.Payload? = null,
) : Aggregate.Default<CustomerAction>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerAction, Long > (key)
}
