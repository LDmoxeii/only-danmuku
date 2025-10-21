package edu.only4.danmuku.application.subscribers.domain.video_comment

import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentHateCountUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 评论点踩数已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CommentHateCountUpdatedDomainEventSubscriber {

    @EventListener(CommentHateCountUpdatedDomainEvent::class)
    fun on(event: CommentHateCountUpdatedDomainEvent) {

    }
}
