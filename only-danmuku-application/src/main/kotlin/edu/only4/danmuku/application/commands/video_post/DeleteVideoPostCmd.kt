package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.VideoDeletePermission
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import org.springframework.stereotype.Service

object DeleteVideoPostCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            Mediator.repositories.remove(
                SVideoPost.predicateById(request.videoId)
            )

            Mediator.uow.save()
        }
    }

    @VideoDeletePermission
    data class Request(
        val videoId: Long,
        val operatorId: Long? = null,
    ) : RequestParam<Unit>
}
