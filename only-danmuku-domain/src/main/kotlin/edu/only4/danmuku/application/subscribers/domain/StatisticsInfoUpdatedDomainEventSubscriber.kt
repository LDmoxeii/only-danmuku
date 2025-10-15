package edu.only4.danmuku.application.subscribers.domain

import edu.only4.danmuku.domain.aggregates.events.StatisticsInfoUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 统计数据已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class StatisticsInfoUpdatedDomainEventSubscriber {

    @EventListener(StatisticsInfoUpdatedDomainEvent::class)
    fun on(event: StatisticsInfoUpdatedDomainEvent) {

    }
}
