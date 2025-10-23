package edu.only4.danmuku.application.subscribers.domain.customer_focus

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.UpdateCustomerStatisticCmd
import edu.only4.danmuku.domain.aggregates.customer_focus.events.UserFocusedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已点赞评论
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class UserFocusedDomainEventSubscriber {

    @EventListener(UserFocusedDomainEvent::class)
    fun on1(event: UserFocusedDomainEvent) {
        val entity = event.entity
        val followerId = entity.customerId.toLong()    // 关注者

        // 1) 关注者的关注数 +1
        Mediator.commands.send(
            UpdateCustomerStatisticCmd.Request(
                targetCustomerId = followerId,
                focusCountDelta = +1
            )
        )

        // TODO: 发送关注通知（站内信/消息中心）
    }

    @EventListener(UserFocusedDomainEvent::class)
    fun on2(event: UserFocusedDomainEvent) {
        val entity = event.entity
        val followeeId = entity.focusCustomerId.toLong() // 被关注者

        // 2) 被关注者的粉丝数 +1
        Mediator.commands.send(
            UpdateCustomerStatisticCmd.Request(
                targetCustomerId = followeeId,
                fansCountDelta = +1
            )
        )

        // TODO: 发送关注通知（站内信/消息中心）
    }
}
