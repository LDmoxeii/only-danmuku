package edu.only4.danmuku.application.commands.video_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.ValidAuditStatus
import edu.only4.danmuku.application.validator.VideoPostExists
import edu.only4.danmuku.application.validator.VideoPostStatusPending
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object AuditVideoPostCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val post = Mediator.repositories.findFirst(
                SVideoPost.predicate { it.id eq request.videoPostId },
            ).getOrNull() ?: throw KnownException("视频草稿不存在：${request.videoPostId}")

            when (request.status) {
                VideoStatus.REVIEW_PASSED -> post.reviewPass()
                VideoStatus.REVIEW_FAILED -> post.reviewFail()
                else -> throw KnownException("不支持的审核状态：${request.status}")
            }

            Mediator.uow.save()
        }
    }

    @VideoPostExists
    @VideoPostStatusPending
    data class Request(
        val videoPostId: Long,
        @field:ValidAuditStatus
        val status: VideoStatus,
        val reason: String = "",
    ) : RequestParam<Unit>
}
