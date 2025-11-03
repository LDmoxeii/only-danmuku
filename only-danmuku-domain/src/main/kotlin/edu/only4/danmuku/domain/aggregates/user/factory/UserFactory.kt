package edu.only4.danmuku.domain.aggregates.user.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import edu.only4.danmuku.domain.aggregates.user.User
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import org.springframework.stereotype.Service

/**
 * 帐号;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "User",
    name = "UserFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class UserFactory : AggregateFactory<UserFactory.Payload, User> {

    override fun create(entityPayload: Payload): User {
        return User(
            type = entityPayload.type,
            email = entityPayload.email,
            nickName = entityPayload.nickName,
            password = entityPayload.registerPassword,
            joinTime = System.currentTimeMillis() / 1000L,
        )
    }

     @Aggregate(
        aggregate = "User",
        name = "UserPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
         val type: UserType,
         val email: String,
         val nickName: String,
         val registerPassword: String,
    ) : AggregatePayload<User>

}
