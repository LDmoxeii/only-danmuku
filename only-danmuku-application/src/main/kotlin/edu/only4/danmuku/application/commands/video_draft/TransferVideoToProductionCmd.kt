package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.video.factory.VideoFactory
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 转移视频草稿及文件到正式表
 */
object TransferVideoToProductionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = request.videoPost

            val existingVideo = Mediator.repositories.findFirst(
                SVideo.predicateById(draft.id),
                persist = true
            ).getOrNull()

            val targetVideo = existingVideo ?: Mediator.factories.create(
                VideoFactory.Payload(videoPost = draft)
            )

            Mediator.uow.save()

            return Response(videoId = targetVideo.id)
        }
    }

    data class Request(
        val videoPost: VideoPost,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
    )
}
