package edu.only4.danmuku.application.subscribers.domain.video_audit_trace

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_post.AuditVideoPostCmd
import edu.only4.danmuku.application.commands.customer_message.SendVideoAuditFailedMessageCmd
import edu.only4.danmuku.application.commands.customer_message.SendVideoAuditPassedMessageCmd
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
import edu.only4.danmuku.domain.aggregates.video_audit_trace.events.VideoAuditTraceRecordedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 审核追溯记录已创建事件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoAuditTraceRecordedDomainEventSubscriber {

    @EventListener(VideoAuditTraceRecordedDomainEvent::class)
    fun updateVideoStatus(event: VideoAuditTraceRecordedDomainEvent) {
        val trace = event.entity
        val status = when (trace.auditStatus) {
            AuditStatus.PASSED -> VideoStatus.REVIEW_PASSED
            AuditStatus.FAILED -> VideoStatus.REVIEW_FAILED
            else -> return
        }
        Mediator.commands.send(
            AuditVideoPostCmd.Request(
                videoPostId = trace.videoPostId,
                status = status,
                reason = trace.reason ?: "",
            )
        )
    }

    @EventListener(VideoAuditTraceRecordedDomainEvent::class)
    fun sendAuditMessage(event: VideoAuditTraceRecordedDomainEvent) {
        val trace = event.entity
        when (trace.auditStatus) {
            AuditStatus.PASSED -> Mediator.commands.send(
                SendVideoAuditPassedMessageCmd.Request(
                    videoId = trace.videoPostId,
                    operatorId = trace.reviewerId
                )
            )
            AuditStatus.FAILED -> Mediator.commands.send(
                SendVideoAuditFailedMessageCmd.Request(
                    videoId = trace.videoPostId,
                    operatorId = trace.reviewerId
                )
            )
            else -> Unit
        }
    }
}
