package edu.only4.danmuku.application.subscribers.domain.video_comment

import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentUntoppedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 评论已取消置顶
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CommentUntoppedDomainEventSubscriber {

    @EventListener(CommentUntoppedDomainEvent::class)
    fun on(event: CommentUntoppedDomainEvent) {

    }
}
