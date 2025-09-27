package com.edu.only4.danmuku.domain.aggregates.customer_profile

import com.edu.only4.danmuku.domain.aggregates.customer_profile.factory.CustomerProfileFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggCustomerProfile (
payload: CustomerProfileFactory.Payload? = null,
) : Aggregate.Default<CustomerProfile>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerProfile, Long > (key)
}
