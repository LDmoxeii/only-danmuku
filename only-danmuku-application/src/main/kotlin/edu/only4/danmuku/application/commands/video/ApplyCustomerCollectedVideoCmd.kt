package edu.only4.danmuku.application.commands.video

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service

/** 将“用户收藏视频”事件落地到统计 */
object ApplyCustomerCollectedVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId)
            ).get()
            video.updateCollectCount(+1)
            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val videoId: Long,
    ) : RequestParam<Response>

    class Response
}
