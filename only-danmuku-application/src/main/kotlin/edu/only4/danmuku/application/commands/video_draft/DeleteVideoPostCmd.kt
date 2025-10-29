package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.VideoDeletePermission
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import org.springframework.stereotype.Service

/**
 * 删除视频
 */
object DeleteVideoPostCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.repositories.remove(
                SVideoPost.predicateById(request.videoId)
            )

            Mediator.Companion.uow.save()
            return Response()
        }
    }

    @VideoDeletePermission
    data class Request(
        val videoId: Long,
        val operatorId: Long? = null,
    ) : RequestParam<Response>

    class Response
}
