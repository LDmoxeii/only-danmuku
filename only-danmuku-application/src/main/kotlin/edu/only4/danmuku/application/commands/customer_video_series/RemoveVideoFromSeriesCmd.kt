package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.SeriesOwnership
import edu.only4.danmuku.application.validater.VideoInSeries
import edu.only4.danmuku.domain._share.meta.customer_video_series.SCustomerVideoSeries
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 从系列中移除视频
 */
object RemoveVideoFromSeriesCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val series = Mediator.repositories.findFirst(
                SCustomerVideoSeries.predicateById(request.seriesId),
                persist = false
            ).getOrNull() ?: throw KnownException("视频系列不存在：${request.seriesId}")

            val removed = series.removeVideo(request.videoId)
            if (removed) {
                Mediator.uow.save()
            }

            return Response(deleted = removed)
        }
    }

    @SeriesOwnership(seriesIdField = "seriesId", operatorIdField = "operatorId")
    @VideoInSeries(seriesIdField = "seriesId", videoIdField = "videoId")
    data class Request(
        /** 系列ID */
        val seriesId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 操作用户ID */
        val operatorId: Long,
    ) : RequestParam<Response>

    data class Response(
        val deleted: Boolean,
    )
}
