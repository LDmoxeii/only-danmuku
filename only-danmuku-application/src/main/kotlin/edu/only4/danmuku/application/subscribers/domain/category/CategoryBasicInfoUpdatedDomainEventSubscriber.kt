package edu.only4.danmuku.application.subscribers.domain.category

import edu.only4.danmuku.domain.aggregates.category.events.CategoryBasicInfoUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 分类基础信息已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class CategoryBasicInfoUpdatedDomainEventSubscriber {

    @EventListener(CategoryBasicInfoUpdatedDomainEvent::class)
    fun on(event: CategoryBasicInfoUpdatedDomainEvent) {

    }
}
