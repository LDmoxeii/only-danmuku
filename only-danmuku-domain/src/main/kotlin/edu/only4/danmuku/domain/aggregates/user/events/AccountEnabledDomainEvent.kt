package edu.only4.danmuku.domain.aggregates.user.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.user.User


/**
 * 帐号已启用
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "User",
    name = "AccountEnabledDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class AccountEnabledDomainEvent(val entity: User)
