package edu.only4.danmuku.application.subscribers.domain.video_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_message.SendVideoAuditFailedMessageCmd
import edu.only4.danmuku.application.commands.video_post.RecordVideoAuditTraceCmd
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
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

    @EventListener(VideoAuditFailedDomainEvent::class)
    fun onAuditTrace(event: VideoAuditFailedDomainEvent) {
        val videoPost = event.entity
        val reason = event.reason
        val reviewerId = event.reviewerId
        val reviewerType = event.reviewerType
        Mediator.commands.send(
            RecordVideoAuditTraceCmd.Request(
                videoPostId = videoPost.id,
                auditStatus = AuditStatus.FAILED,
                reviewerId = reviewerId,
                reviewerType = reviewerType,
                reason = reason,
                occurTime = System.currentTimeMillis() / 1000L
            )
        )
    }
}
