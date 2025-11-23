package edu.only4.danmuku.application.commands.video_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.VideoPostExists
import edu.only4.danmuku.application.validator.VideoPostEditableStatus
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.PostType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object UpdateVideoPostCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = Mediator.repositories.findFirst(
                SVideoPost.predicate { it.id eq request.videoPostId },
            ).getOrNull() ?: throw KnownException("视频草稿不存在: ${request.videoPostId}")

            if (draft.customerId != request.customerId) {
                throw KnownException("无权编辑该视频草稿")
            }

            // 将实体加入工作单元，确保在 save() 时可被 merge/flush/refresh
            Mediator.uow.persist(draft)

            val basicChanged = draft.applyBasicInfo(
                videoName = request.videoName,
                videoCover = request.videoCover,
                pCategoryId = request.pCategoryId,
                categoryId = request.categoryId,
                postType = request.postType,
                originInfo = request.originInfo,
                tags = request.tags,
                introduction = request.introduction,
                interaction = request.interaction,
            )

            if (basicChanged) {
                draft.markPendingReview()
            }

            Mediator.uow.save()
            return Response(videoId = draft.id)
        }
    }

    @VideoPostExists
    @VideoPostEditableStatus
    data class Request(
        val videoPostId: Long,
        val customerId: Long,
        val videoName: String? = null,
        val videoCover: String? = null,
        val pCategoryId: Long? = null,
        val categoryId: Long? = null,
        val postType: PostType? = null,
        val originInfo: String? = null,
        val tags: String? = null,
        val introduction: String? = null,
        val interaction: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
    )
}
