package edu.only4.danmuku.domain.aggregates.video_audit_trace.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_audit_trace.VideoAuditTrace


/**
 * 审核追溯记录已创建事件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoAuditTrace",
    name = "VideoAuditTraceRecordedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoAuditTraceRecordedDomainEvent(val entity: VideoAuditTrace)
