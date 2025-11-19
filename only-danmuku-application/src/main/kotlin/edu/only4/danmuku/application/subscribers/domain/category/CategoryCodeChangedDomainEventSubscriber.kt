package edu.only4.danmuku.application.subscribers.domain.category

import edu.only4.danmuku.domain.aggregates.category.events.CategoryCodeChangedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 分类编码已更改
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class CategoryCodeChangedDomainEventSubscriber {

    @EventListener(CategoryCodeChangedDomainEvent::class)
    fun on(event: CategoryCodeChangedDomainEvent) {

    }
}
