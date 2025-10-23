package edu.only4.danmuku.domain.aggregates.customer_focus.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus
import org.springframework.stereotype.Service

/**
 * 用户关注;
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
        val entity = CustomerFocus(
            id = 0L,
            customerId = payload.customerId,
            focusCustomerId = payload.focusCustomerId,
            createUserId = null,
            createBy = null,
            createTime = null,
            updateUserId = null,
            updateBy = null,
            updateTime = null,
            deleted = 0L,
        )
        return entity
    }

     @Aggregate(
        aggregate = "CustomerFocus",
        name = "CustomerFocusPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
         val customerId: String,
         val focusCustomerId: String,
     ) : AggregatePayload<CustomerFocus>

}
