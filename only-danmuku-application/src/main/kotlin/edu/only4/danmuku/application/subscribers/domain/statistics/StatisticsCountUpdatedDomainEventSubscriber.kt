package edu.only4.danmuku.application.subscribers.domain.statistics

import edu.only4.danmuku.domain.aggregates.statistics.events.StatisticsCountUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 统计计数已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class StatisticsCountUpdatedDomainEventSubscriber {

    @EventListener(StatisticsCountUpdatedDomainEvent::class)
    fun on(event: StatisticsCountUpdatedDomainEvent) {

    }
}
