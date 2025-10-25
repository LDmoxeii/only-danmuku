package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_draft.enums.VideoStatus
import edu.only4.danmuku.domain.aggregates.video_draft.factory.VideoDraftFactory
import org.springframework.stereotype.Service

/**
 * 创建视频草稿
 */
object CreateVideoDraftCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = Mediator.factories.create(
                VideoDraftFactory.Payload(
                    videoId = request.videoId,
                    customerId = request.customerId,
                    videoName = request.videoName,
                    videoCover = request.videoCover,
                    pCategoryId = request.pCategoryId,
                    categoryId = request.categoryId,
                    postType = request.postType,
                    originInfo = request.originInfo,
                    tags = request.tags,
                    introduction = request.introduction,
                    status = VideoStatus.TRANSCODING,
                )
            )
            Mediator.uow.save()

            return Response(videoId = draft.id)
        }
    }

    data class Request(
        val videoId: Long,
        val customerId: Long,
        val videoName: String,
        val videoCover: String? = null,
        val pCategoryId: Long,
        val categoryId: Long? = null,
        val postType: PostType = PostType.valueOf(1),
        val originInfo: String? = null,
        val tags: String? = null,
        val introduction: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
    )
}
