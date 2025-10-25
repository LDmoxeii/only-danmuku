package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 切换视频推荐状态
 */
object RecommendVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId)
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            // 切换推荐状态（推荐 <-> 未推荐）
            video.toggleRecommend()

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 视频ID */
        val videoId: Long
    ) : RequestParam<Response>

    class Response
}
