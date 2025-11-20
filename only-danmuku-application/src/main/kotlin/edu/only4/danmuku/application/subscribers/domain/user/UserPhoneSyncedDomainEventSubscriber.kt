package edu.only4.danmuku.application.subscribers.domain.user

import edu.only4.danmuku.domain.aggregates.user.events.UserPhoneSyncedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户手机号已同步
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class UserPhoneSyncedDomainEventSubscriber {

    @EventListener(UserPhoneSyncedDomainEvent::class)
    fun on(event: UserPhoneSyncedDomainEvent) {

    }
}
