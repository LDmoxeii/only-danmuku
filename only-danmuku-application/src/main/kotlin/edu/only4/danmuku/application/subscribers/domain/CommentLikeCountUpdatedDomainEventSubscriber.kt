package edu.only4.danmuku.application.subscribers.domain

import edu.only4.danmuku.domain.aggregates.events.CommentLikeCountUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 评论点赞数已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CommentLikeCountUpdatedDomainEventSubscriber {

    @EventListener(CommentLikeCountUpdatedDomainEvent::class)
    fun on(event: CommentLikeCountUpdatedDomainEvent) {

    }
}
