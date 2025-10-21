package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 修改视频互动设置
 */
object ChangeVideoInteractionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId),
                persist = false
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            if (video.customerId != request.userId) {
                throw KnownException("无权限修改该视频互动设置")
            }

            video.changeInteraction(request.interaction)

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 视频ID */
        val videoId: Long,
        /** 用户ID */
        val userId: Long,
        /** 互动设置 */
        val interaction: String?
    ) : RequestParam<Response>

    class Response
}
