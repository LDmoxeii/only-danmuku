package edu.only4.danmuku.domain.aggregates.customer_message.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage


/**
 * 评论回复消息已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "CustomerMessage",
    name = "CustomerMessageCommentReplyCreatedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class CustomerMessageCommentReplyCreatedDomainEvent(val entity: CustomerMessage)
