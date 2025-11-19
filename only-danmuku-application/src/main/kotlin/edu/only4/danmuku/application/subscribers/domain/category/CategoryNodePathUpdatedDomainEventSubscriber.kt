package edu.only4.danmuku.application.subscribers.domain.category

import edu.only4.danmuku.domain.aggregates.category.events.CategoryNodePathUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 分类节点路径已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class CategoryNodePathUpdatedDomainEventSubscriber {

    @EventListener(CategoryNodePathUpdatedDomainEvent::class)
    fun on(event: CategoryNodePathUpdatedDomainEvent) {

    }
}
