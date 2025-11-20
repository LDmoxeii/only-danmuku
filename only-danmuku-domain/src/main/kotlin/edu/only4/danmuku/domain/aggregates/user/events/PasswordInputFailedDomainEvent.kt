package edu.only4.danmuku.domain.aggregates.user.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.user.User


/**
 * 密码输入失败领域事件（用于触发异常操作统计）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "User",
    name = "PasswordInputFailedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class PasswordInputFailedDomainEvent(val entity: User)
