package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
import edu.only4.danmuku.domain.aggregates.video_audit_trace.factory.VideoAuditTraceFactory

import org.springframework.stereotype.Service

/**
 * 记录视频审核追溯日志
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
object RecordVideoAuditTraceCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val occurTime = request.occurTime ?: (System.currentTimeMillis() / 1000L)
            val trace = Mediator.factories.create(
                VideoAuditTraceFactory.Payload(
                    videoPostId = request.videoPostId,
                    auditStatus = request.auditStatus,
                    reviewerId = request.reviewerId,
                    reviewerType = request.reviewerType,
                    reason = request.reason,
                    occurTime = occurTime
                )
            )

            Mediator.uow.save()

            return Response(
                traceId = trace.id
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val auditStatus: AuditStatus,
        val reviewerId: Long?,
        val reviewerType: UserType,
        val reason: String? = null,
        val occurTime: Long? = null,
    ) : RequestParam<Response>

    data class Response(
        val traceId: Long
    )
}
