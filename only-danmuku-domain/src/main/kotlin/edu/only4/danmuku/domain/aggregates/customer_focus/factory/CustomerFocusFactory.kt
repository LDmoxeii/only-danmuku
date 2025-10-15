package edu.only4.danmuku.domain.aggregates.customer_focus.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus

import org.springframework.stereotype.Service

/**
 * 用户关注;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "CustomerFocus",
    name = "CustomerFocusFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class CustomerFocusFactory : AggregateFactory<CustomerFocusFactory.Payload, CustomerFocus> {

    override fun create(payload: Payload): CustomerFocus {
        return CustomerFocus(

        )
    }

     @Aggregate(
        aggregate = "CustomerFocus",
        name = "CustomerFocusPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<CustomerFocus>

}
