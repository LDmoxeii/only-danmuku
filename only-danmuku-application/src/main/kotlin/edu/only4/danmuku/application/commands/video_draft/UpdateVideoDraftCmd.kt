package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.VideoDraftExists
import edu.only4.danmuku.application.validater.VideoEditableStatus
import edu.only4.danmuku.domain._share.meta.video_draft.SVideoDraft
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新视频草稿
 */
object UpdateVideoDraftCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = Mediator.repositories.findFirst(
                SVideoDraft.predicate { it.id eq request.videoId },
                persist = false
            ).getOrNull() ?: throw KnownException("视频草稿不存在：${request.videoId}")

            if (draft.customerId != request.customerId) {
                throw KnownException("无权编辑该视频草稿")
            }

            request.videoName?.let { draft.videoName = it }
            request.videoCover?.let { draft.videoCover = it }
            request.pCategoryId?.let { draft.pCategoryId = it }
            draft.categoryId = request.categoryId
            draft.postType = request.postType ?: draft.postType
            draft.originInfo = request.originInfo
            draft.tags = request.tags
            draft.introduction = request.introduction

            // 仅修改基础信息：标记为待审核
            draft.markPendingReview()

            Mediator.uow.save()
            return Response(videoId = draft.id)
        }
    }

    @VideoDraftExists
    @VideoEditableStatus
    data class Request(
        val videoId: Long,
        val customerId: Long,
        val videoName: String? = null,
        val videoCover: String? = null,
        val pCategoryId: Long? = null,
        val categoryId: Long? = null,
        val postType: PostType? = null,
        val originInfo: String? = null,
        val tags: String? = null,
        val introduction: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
    )
}
