package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 根据文件转码结果刷新视频草稿状态
 */
object RefreshVideoDraftTranscodeStatusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = Mediator.repositories.findFirst(
                SVideoPost.predicate { it.id eq request.videoId },
                persist = true
            ).getOrNull() ?: throw KnownException("视频草稿不存在: ${request.videoId}")

            val hasFailedFiles = draft.videoFilePosts.any { it.isTransferFailed() }
            val hasTranscodingFiles = draft.videoFilePosts.any { it.isTranscoding() }

            val targetStatus = when {
                hasFailedFiles -> {
                    draft.markTranscodeFailed()
                    VideoStatus.TRANSCODE_FAILED
                }

                hasTranscodingFiles -> {
                    draft.markTranscoding()
                    VideoStatus.TRANSCODING
                }

                else -> {
                    draft.markPendingReview()
                    val totalDuration = draft.videoFilePosts.mapNotNull { it.duration }.sum()
                    draft.updateDuration(totalDuration)
                    VideoStatus.PENDING_REVIEW
                }
            }

            Mediator.uow.save()
            return Response(videoId = draft.id, status = targetStatus)
        }
    }

    data class Request(
        val videoId: Long,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
        val status: VideoStatus,
    )
}
