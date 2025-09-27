package com.edu.only4.danmuku.domain.aggregates.customer_focus

import com.edu.only4.danmuku.domain.aggregates.customer_focus.factory.CustomerFocusFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggCustomerFocus (
payload: CustomerFocusFactory.Payload? = null,
) : Aggregate.Default<CustomerFocus>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerFocus, Long > (key)
}
