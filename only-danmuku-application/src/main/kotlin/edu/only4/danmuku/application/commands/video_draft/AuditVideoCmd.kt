package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.ValidAuditStatus
import edu.only4.danmuku.application.validater.VideoDraftExists
import edu.only4.danmuku.application.validater.VideoDraftStatusPending
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

            val statusEnum = runCatching {
                VideoStatus.valueOf(request.status)
            }.getOrElse {
                throw KnownException("无效的审核状态: ${request.status}")
            }

            when (statusEnum) {
                VideoStatus.REVIEW_PASSED -> draft.reviewPass()
                VideoStatus.REVIEW_FAILED -> draft.reviewFail()
                else -> throw KnownException("不支持的审核状态: ${statusEnum.code}")
            }

            Mediator.uow.save()
            return Response()
        }
    }

    @VideoDraftExists
    @VideoDraftStatusPending
    data class Request(
        /** 视频ID */
        val videoId: Long,
        /** 审核状态: 4-审核通过 5-审核不通过 */
        @field:ValidAuditStatus
        val status: Int,
        /** 审核原因（预留） */
        val reason: String? = null,
    ) : RequestParam<Response>

    class Response
}
