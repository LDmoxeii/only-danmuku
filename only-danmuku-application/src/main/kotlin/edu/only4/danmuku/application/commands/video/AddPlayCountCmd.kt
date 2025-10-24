package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

/**
 * 增加播放数
 */
object AddPlayCountCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId),
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            // 增加播放数，并记录最后播放时间
            video.applyStatisticsDelta(playCountDelta = 1)
            video.lastPlayTime = LocalDateTime.now()

            Mediator.uow.save()

            return Response(
                videoId = video.id,
                playCount = video.playCount
            )
        }
    }

    data class Request(
        val videoId: Long
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
        val playCount: Int?
    )
}
