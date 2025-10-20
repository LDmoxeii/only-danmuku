package edu.only4.danmuku.domain.aggregates.customer_profile.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile

import org.springframework.stereotype.Service

/**
 * 用户信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "CustomerProfile",
    name = "CustomerProfileFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class CustomerProfileFactory : AggregateFactory<CustomerProfileFactory.Payload, CustomerProfile> {

    override fun create(entityPayload: Payload): CustomerProfile {
        return CustomerProfile(
            userId = entityPayload.userid,
            nickName = entityPayload.nickName,
            email = entityPayload.email,
        )
    }

    @Aggregate(
        aggregate = "CustomerProfile",
        name = "CustomerProfilePayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val userid: Long,
        val nickName: String,
        val email: String,
    ) : AggregatePayload<CustomerProfile>

}
