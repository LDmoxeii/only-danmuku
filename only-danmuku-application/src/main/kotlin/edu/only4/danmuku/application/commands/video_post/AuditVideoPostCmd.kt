package edu.only4.danmuku.application.commands.video_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.ValidAuditStatus
import edu.only4.danmuku.application.validator.VideoPostExists
import edu.only4.danmuku.application.validator.VideoPostStatusPending
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object AuditVideoPostCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val post = Mediator.repositories.findFirst(
                SVideoPost.predicate { it.id eq request.videoPostId },
            ).getOrNull() ?: throw KnownException("视频草稿不存在：${request.videoPostId}")

            val statusEnum = runCatching {
                VideoStatus.valueOf(request.status)
            }.getOrElse {
                throw KnownException("无效的审核状态: ${request.status}")
            }

            when (statusEnum) {
                VideoStatus.REVIEW_PASSED -> post.reviewPass()
                VideoStatus.REVIEW_FAILED -> post.reviewFail()
                else -> throw KnownException("不支持的审核状态: ${statusEnum.code}")
            }

            Mediator.uow.save()
            return Response()
        }
    }

    @VideoPostExists
    @VideoPostStatusPending
    data class Request(
        /** 视频ID */
        val videoPostId: Long,
        /** 审核状态: 4-审核通过 5-审核不通过 */
        @field:ValidAuditStatus
        val status: Int,
        /** 审核原因（预留） */
        val reason: String? = null,
    ) : RequestParam<Response>

    class Response
}
