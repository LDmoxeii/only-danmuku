package edu.only4.danmuku.domain.aggregates.customer_action.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction

/**
 * 用户已取消收藏视频
 *
 * @author cap4k-ddd-codegen
 * @date 2025/10/23
 */
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "CustomerAction",
    name = "CustomerUncollectedVideoDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = "用户取消收藏视频"
)
class CustomerUncollectedVideoDomainEvent(val entity: CustomerAction)
