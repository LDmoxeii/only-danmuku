package edu.only4.danmuku.domain.aggregates.customer_profile.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile


/**
 * 客户奖励金币已回收
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "CustomerProfile",
    name = "CustomerProfileRewardCoinsReclaimedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class CustomerProfileRewardCoinsReclaimedDomainEvent(val entity: CustomerProfile, deduction: Int)
