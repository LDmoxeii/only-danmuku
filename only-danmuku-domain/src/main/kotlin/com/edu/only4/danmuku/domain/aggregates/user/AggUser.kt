package com.edu.only4.danmuku.domain.aggregates.user

import com.edu.only4.danmuku.domain.aggregates.user.factory.UserFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggUser (
payload: UserFactory.Payload? = null,
) : Aggregate.Default<User>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggUser, Long > (key)
}
