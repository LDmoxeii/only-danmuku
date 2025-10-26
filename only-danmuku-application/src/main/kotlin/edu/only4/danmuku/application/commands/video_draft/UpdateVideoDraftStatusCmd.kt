package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.VideoDraftExists
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 修改视频草稿状态
 */
object UpdateVideoDraftStatusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = Mediator.repositories.findFirst(
                SVideoPost.predicate { it.id eq request.videoId },
                persist = false
            ).getOrNull() ?: throw KnownException("视频草稿不存在：${request.videoId}")

            when (request.status) {
                VideoStatus.TRANSCODING -> draft.markTranscoding()
                VideoStatus.TRANSCODE_FAILED -> draft.markTranscodeFailed()
                VideoStatus.PENDING_REVIEW -> draft.markPendingReview()
                VideoStatus.REVIEW_PASSED -> draft.reviewPass()
                VideoStatus.REVIEW_FAILED -> draft.reviewFail()
                else -> { /* ignore unknown */ }
            }

            Mediator.uow.save()
            return Response(videoId = draft.id, status = draft.status)
        }
    }

    @VideoDraftExists
    data class Request(
        val videoId: Long,
        val status: VideoStatus,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
        val status: VideoStatus,
    )
}

