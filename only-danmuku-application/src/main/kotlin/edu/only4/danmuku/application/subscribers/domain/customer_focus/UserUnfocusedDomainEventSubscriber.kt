package edu.only4.danmuku.application.subscribers.domain.customer_focus

import edu.only4.danmuku.domain.aggregates.customer_focus.events.UserUnfocusedDomainEvent
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
class UserUnfocusedDomainEventSubscriber {

    @EventListener(UserUnfocusedDomainEvent::class)
    fun on(event: UserUnfocusedDomainEvent) {
        val entity = event.entity

        // TODO: 发送取消关注通知（可选）
    }
}
