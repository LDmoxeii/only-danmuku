package edu.only4.danmuku.application.subscribers.domain.video_comment

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.ApplyVideoCommentCountDeltaCmd
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 评论已删除
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CommentDeletedDomainEventSubscriber {

    @EventListener(CommentDeletedDomainEvent::class)
    fun on(event: CommentDeletedDomainEvent) {
        val comment = event.entity
        if (!(comment.isRootComment())) return
        Mediator.commands.send(
            ApplyVideoCommentCountDeltaCmd.Request(
                videoId = comment.videoId,
                delta = -1
            )
        )
    }

    @EventListener(CommentDeletedDomainEvent::class)
    fun on1(event: CommentDeletedDomainEvent) {
        val comment = event.entity
        // TODO 发送通知
    }
}
