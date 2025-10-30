package edu.only4.danmuku.application.subscribers.domain.video_draft

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_message.SendVideoAuditFailedMessageCmd
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoAuditFailedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频审核失败
 */
@Service
class VideoAuditFailedDomainEventSubscriber {

    @EventListener(VideoAuditFailedDomainEvent::class)
    fun on(event: VideoAuditFailedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            SendVideoAuditFailedMessageCmd.Request(
                videoId = videoPost.id,
                operatorId = null
            )
        )
    }
}
