package edu.only4.danmuku.application.subscribers.domain.customer_focus

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
    fun on(event: UserFocusedDomainEvent) {
        val entity = event.entity

        // TODO: 发送关注通知（站内信/消息中心）
    }
}
