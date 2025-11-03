package edu.only4.danmuku.domain.aggregates.customer_action.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
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

    override fun create(entityPayload: Payload): CustomerAction {
        val entity = CustomerAction(
            customerId = entityPayload.customerId,
            videoId = entityPayload.videoId,
            videoOwnerId = entityPayload.videoOwnerId,
            commentId = entityPayload.commentId,
            actionType = entityPayload.actionType,
            actionCount = entityPayload.actionCount,
            actionTime = System.currentTimeMillis() / 1000
        )
        return entity
    }

     @Aggregate(
        aggregate = "CustomerAction",
        name = "CustomerActionPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
         var customerId: Long = 0L,
         var videoId: Long = 0L,
         var videoOwnerId: Long = 0L,
         var commentId: Long = 0L,
         var actionType: ActionType = ActionType.valueOf(0),
         var actionCount: Int = 0,
    ) : AggregatePayload<CustomerAction>

}
