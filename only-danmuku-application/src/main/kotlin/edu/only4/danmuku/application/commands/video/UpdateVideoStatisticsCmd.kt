package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新视频统计信息
 */
object UpdateVideoStatisticsCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId),
                persist = false
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            video.applyStatisticsDelta(
                playCountDelta = request.playCountDelta,
                likeCountDelta = request.likeCountDelta,
                danmukuCountDelta = request.danmukuCountDelta,
                commentCountDelta = request.commentCountDelta,
                coinCountDelta = request.coinCountDelta,
                collectCountDelta = request.collectCountDelta
            )

            Mediator.uow.save()

            return Response(
                videoId = video.id,
                playCount = video.playCount,
                likeCount = video.likeCount,
                danmukuCount = video.danmukuCount,
                commentCount = video.commentCount,
                coinCount = video.coinCount,
                collectCount = video.collectCount
            )
        }
    }

    data class Request(
        val videoId: Long,
        val playCountDelta: Int? = null,
        val likeCountDelta: Int? = null,
        val danmukuCountDelta: Int? = null,
        val commentCountDelta: Int? = null,
        val coinCountDelta: Int? = null,
        val collectCountDelta: Int? = null,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
        val playCount: Int?,
        val likeCount: Int?,
        val danmukuCount: Int?,
        val commentCount: Int?,
        val coinCount: Int?,
        val collectCount: Int?,
    )
}
