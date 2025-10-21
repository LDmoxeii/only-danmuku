package edu.only4.danmuku.application.subscribers.domain.statistics

import edu.only4.danmuku.domain.aggregates.statistics.events.StatisticsInfoUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 统计数据已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class StatisticsInfoUpdatedDomainEventSubscriber {

    @EventListener(StatisticsInfoUpdatedDomainEvent::class)
    fun on(event: StatisticsInfoUpdatedDomainEvent) {

    }
}
