package edu.only4.danmuku.application.commands.video

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.VideoDeletePermission
import edu.only4.danmuku.application.validater.VideoExists
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service

/**
 * 删除视频
 */
object DeleteVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.repositories.remove(SVideo.predicateById(request.videoId))

            Mediator.uow.save()
            return Response()
        }
    }

    @VideoDeletePermission
    data class Request(
        /** 视频ID */
        @field:VideoExists
        val videoId: Long,
        /** 操作者ID，null 表示管理员 */
        val operatorId: Long? = null,
    ) : RequestParam<Response>

    class Response
}

