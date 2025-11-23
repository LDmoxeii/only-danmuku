package edu.only4.danmuku.application.subscribers.domain.video_post

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
    }
}
