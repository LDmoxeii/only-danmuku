package edu.only4.danmuku.application.subscribers.domain.category

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.category.FinalizeCategoryAfterCreateCmd
import edu.only4.danmuku.domain.aggregates.category.events.CategoryCreatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 分类已创建 → 触发二阶段命令回填排序与路径
 */
@Service
class CategoryCreatedDomainEventSubscriber {

    @EventListener(CategoryCreatedDomainEvent::class)
    fun on1(event: CategoryCreatedDomainEvent) {
        Mediator.commands.send(
            FinalizeCategoryAfterCreateCmd.Request(categoryId = event.id)
        )
    }
}
