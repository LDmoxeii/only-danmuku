package edu.only4.danmuku.domain.aggregates.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile


/**
 * 用户统计信息已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "CustomerProfile",
    name = "CustomerStatisticUpdatedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class CustomerStatisticUpdatedDomainEvent(val entity: CustomerProfile)
