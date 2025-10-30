package edu.only4.danmuku.application.subscribers.domain.video_comment

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_message.SendCommentMessageCmd
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentRepliedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 评论已回复
 */
@Service
class CommentRepliedDomainEventSubscriber {

    @EventListener(CommentRepliedDomainEvent::class)
    fun on(event: CommentRepliedDomainEvent) {
        val c = event.entity
        Mediator.commands.send(
            SendCommentMessageCmd.Request(
                videoId = c.videoId,
                sendUserId = c.customerId,
                content = c.content,
                replyCommentId = c.parentId,
                replyCommentContent = null,
            )
        )
    }
}
