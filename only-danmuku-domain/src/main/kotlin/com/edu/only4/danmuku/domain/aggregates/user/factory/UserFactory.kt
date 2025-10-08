package com.edu.only4.danmuku.domain.aggregates.user.factory

import com.edu.only4.danmuku.domain.aggregates.user.User
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "User", name = "UserFactory", type = Aggregate.TYPE_FACTORY, description = "")
class UserFactory : AggregateFactory<UserFactory.Payload, User> {
    override fun create(entityPayload: Payload): $ {
        Entity
    }

    {
        return User(

        )
    }

    @Aggregate(aggregate = "User", name = "UserPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<User>
}
