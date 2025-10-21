package edu.only4.danmuku.application.subscribers.domain.statistics

import edu.only4.danmuku.domain.aggregates.statistics.events.StatisticsInfoAddedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 统计数据已添加
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class StatisticsInfoAddedDomainEventSubscriber {

    @EventListener(StatisticsInfoAddedDomainEvent::class)
    fun on(event: StatisticsInfoAddedDomainEvent) {

    }
}
