package edu.only4.danmuku.application.subscribers.domain.category

import edu.only4.danmuku.domain.aggregates.category.events.CategoryDeletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 分类已删除
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CategoryDeletedDomainEventSubscriber {

    @EventListener(CategoryDeletedDomainEvent::class)
    fun on(event: CategoryDeletedDomainEvent) {
        // TODO: 刷新 Redis 分类树缓存
    }
}
