package edu.only4.danmuku.application.subscribers.domain.user

import edu.only4.danmuku.domain.aggregates.user.events.PasswordInputFailedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 密码输入失败领域事件（用于触发异常操作统计）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class PasswordInputFailedDomainEventSubscriber {

    @EventListener(PasswordInputFailedDomainEvent::class)
    fun on(event: PasswordInputFailedDomainEvent) {

    }
}
