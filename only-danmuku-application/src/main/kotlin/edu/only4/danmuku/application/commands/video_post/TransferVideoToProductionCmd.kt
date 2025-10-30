package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain.aggregates.video.factory.VideoFactory
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import org.springframework.stereotype.Service

object TransferVideoToProductionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val post = request.videoPost

            val targetVideo = Mediator.factories.create(
                VideoFactory.Payload(videoPost = post)
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
