package edu.only4.danmuku.application.subscribers.domain.video_comment

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.UpdateVideoStatisticsCmd
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentPostedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 评论已发表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CommentPostedDomainEventSubscriber {

    @EventListener(CommentPostedDomainEvent::class)
    fun on(event: CommentPostedDomainEvent) {
        val comment = event.entity
        if (comment.parentId == 0L) {
            Mediator.commands.send(
                UpdateVideoStatisticsCmd.Request(
                    videoId = comment.videoId,
                    commentCountDelta = 1
                )
            )
        }
    }
}
