package edu.only4.danmuku.domain.aggregates.video_audit_trace.specs

import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_audit_trace.VideoAuditTrace

import org.springframework.stereotype.Service

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义规约方法
 * @author cap4k-ddd-codegen
 */
@Service
@Aggregate(
    aggregate = "VideoAuditTrace",
    name = "VideoAuditTraceSpecification",
    type = Aggregate.TYPE_SPECIFICATION,
    description = ""
)
class VideoAuditTraceSpecification : Specification<VideoAuditTrace> {

    override fun specify(entity: VideoAuditTrace): Result {
        return Result.pass()
    }

}
