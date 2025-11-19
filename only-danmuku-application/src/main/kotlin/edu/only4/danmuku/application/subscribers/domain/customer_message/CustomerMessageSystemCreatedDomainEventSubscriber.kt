package edu.only4.danmuku.application.subscribers.domain.customer_message

import edu.only4.danmuku.domain.aggregates.customer_message.events.CustomerMessageSystemCreatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 系统消息已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class CustomerMessageSystemCreatedDomainEventSubscriber {

    @EventListener(CustomerMessageSystemCreatedDomainEvent::class)
    fun on(event: CustomerMessageSystemCreatedDomainEvent) {

    }
}
