package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_draft.SVideoDraft
import edu.only4.danmuku.domain.aggregates.video_draft.enums.VideoStatus

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 审核视频
 */
object AuditVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 根据视频ID找到对应草稿
            val draft = Mediator.repositories.findFirst(
                SVideoDraft.predicate { it.videoId eq request.videoId },
                persist = false
            ).getOrNull() ?: throw KnownException("视频草稿不存在：${request.videoId}")

            val statusEnum = when (request.status) {
                0 -> VideoStatus.PENDING_REVIEW
                1 -> VideoStatus.REVIEW_PASSED
                2 -> VideoStatus.REVIEW_FAILED
                else -> throw KnownException("无效的审核状态: ${request.status}")
            }

            when (statusEnum) {
                VideoStatus.PENDING_REVIEW -> draft.markPendingReview()
                VideoStatus.REVIEW_PASSED -> draft.reviewPass()
                VideoStatus.REVIEW_FAILED -> draft.reviewFail()
                else -> throw KnownException("不支持的审核状态: $statusEnum")
            }

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 视频ID */
        val videoId: Long,
        /** 审核状态: 0-审核中 1-审核通过 2-审核不通过 */
        val status: Int,
        /** 审核原因（预留） */
        val reason: String? = null,
    ) : RequestParam<Response>

    class Response
}
