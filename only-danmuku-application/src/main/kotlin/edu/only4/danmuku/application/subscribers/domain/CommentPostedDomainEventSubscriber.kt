package edu.only4.danmuku.application.subscribers.domain

import edu.only4.danmuku.domain.aggregates.events.CommentPostedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 评论已发表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CommentPostedDomainEventSubscriber {

    @EventListener(CommentPostedDomainEvent::class)
    fun on(event: CommentPostedDomainEvent) {

    }
}
