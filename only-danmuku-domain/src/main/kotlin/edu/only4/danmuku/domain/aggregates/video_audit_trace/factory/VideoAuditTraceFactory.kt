package edu.only4.danmuku.domain.aggregates.video_audit_trace.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_audit_trace.VideoAuditTrace
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus

import org.springframework.stereotype.Service

/**
 * 视频审核追溯记录;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
@Aggregate(
    aggregate = "VideoAuditTrace",
    name = "VideoAuditTraceFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoAuditTraceFactory : AggregateFactory<VideoAuditTraceFactory.Payload, VideoAuditTrace> {

    override fun create(entityPayload: Payload): VideoAuditTrace {
        return VideoAuditTrace(
            videoPostId = entityPayload.videoPostId,
            auditStatus = entityPayload.auditStatus,
            reviewerId = entityPayload.reviewerId,
            reviewerType = entityPayload.reviewerType,
            reason = entityPayload.reason,
            occurTime = entityPayload.occurTime
        )
    }

    @Aggregate(aggregate = "VideoAuditTrace", name = "VideoAuditTracePayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    data class Payload(
         val videoPostId: Long,
         val auditStatus: AuditStatus,
         val reviewerId: Long?,
         val reviewerType: UserType,
         val reason: String?,
         val occurTime: Long,
    ) : AggregatePayload<VideoAuditTrace>

}
