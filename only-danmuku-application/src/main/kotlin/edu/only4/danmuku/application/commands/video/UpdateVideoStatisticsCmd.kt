package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新视频统计信息
 */
@Deprecated("Use ApplyVideo*CountDeltaCmd instead for per-stat updates.")
object UpdateVideoStatisticsCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId),
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
        }
    }

    data class Request(
        val videoId: Long,
        val playCountDelta: Int = 0,
        val likeCountDelta: Int = 0,
        val danmukuCountDelta: Int = 0,
        val commentCountDelta: Int = 0,
        val coinCountDelta: Int = 0,
        val collectCountDelta: Int = 0,
    ) : RequestParam<Unit>
}
