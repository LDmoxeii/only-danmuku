package edu.only4.danmuku.domain.aggregates.user.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.user.User


/**
 * 用户关系已绑定
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "User",
    name = "RelationshipBoundDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class RelationshipBoundDomainEvent(val entity: User)
