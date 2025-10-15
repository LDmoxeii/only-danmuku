package edu.only4.danmuku.domain.aggregates.customer_action.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction

import org.springframework.stereotype.Service

/**
 * 用户行为 点赞、评论;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "CustomerAction",
    name = "CustomerActionFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class CustomerActionFactory : AggregateFactory<CustomerActionFactory.Payload, CustomerAction> {

    override fun create(payload: Payload): CustomerAction {
        return CustomerAction(

        )
    }

     @Aggregate(
        aggregate = "CustomerAction",
        name = "CustomerActionPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<CustomerAction>

}
