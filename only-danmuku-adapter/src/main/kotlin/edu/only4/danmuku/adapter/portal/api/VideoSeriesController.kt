package edu.only4.danmuku.adapter.portal.api

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.customer_video_series.DeleteCustomerVideoSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.RemoveVideoFromSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.UpdateCustomerVideoSeriesSortCmd
import edu.only4.danmuku.application.commands.customer_video_series.UpdateCustomerVideoSeriesVideosCmd
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 视频系列控制器
 */
@RestController
@RequestMapping("/uhome/series")
@Validated
class VideoSeriesController {

    /**
     * 加载视频系列列表
     */
    @PostMapping("/loadVideoSeries")
    fun videoSeriesLoad(@RequestBody @Validated request: VideoSeriesLoad.Request): VideoSeriesLoad.Response {
        // 调用查询获取用户所有系列
        val seriesList = Mediator.queries.send(
            GetCustomerVideoSeriesListQry.Request(userId = request.userId.toLong())
        )

        return VideoSeriesLoad.Response(
            list = seriesList.map { series ->
                VideoSeriesLoad.SeriesItem(
                    seriesId = series.seriesId.toString(),
                    seriesName = series.seriesName,
                    seriesDescription = series.seriesDescription,
                    sort = series.sort,
                    videoCount = series.videoCount,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(series.createTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
            }
        )
    }

    /**
     * 保存系列视频
     */
    @PostMapping("/saveSeriesVideo")
    fun videoSeriesSaveVideo(@RequestBody @Validated request: VideoSeriesSaveVideo.Request): VideoSeriesSaveVideo.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            UpdateCustomerVideoSeriesVideosCmd.Request(
                userId = userId,
                seriesId = request.seriesId.toLong(),
                videoIds = request.videoIds,
                isDelete = false
            )
        )

        return VideoSeriesSaveVideo.Response()
    }

    /**
     * 删除系列中的视频
     */
    @PostMapping("/delSeriesVideo")
    fun videoSeriesDelVideo(@RequestBody @Validated request: VideoSeriesDelVideo.Request): VideoSeriesDelVideo.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            RemoveVideoFromSeriesCmd.Request(
                seriesId = request.seriesId,
                videoId = request.videoId,
                operatorId = userId
            )
        )

        return VideoSeriesDelVideo.Response()
    }

    /**
     * 删除视频系列
     */
    @PostMapping("/delVideoSeries")
    fun videoSeriesDel(@RequestBody @Validated request: VideoSeriesDel.Request): VideoSeriesDel.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            DeleteCustomerVideoSeriesCmd.Request(
                userId = userId,
                seriesId = request.seriesId.toLong()
            )
        )

        return VideoSeriesDel.Response()
    }

    /**
     * 调整系列排序
     */
    @PostMapping("/changeVideoSeriesSort")
    fun videoSeriesChangeSort(@RequestBody @Validated request: VideoSeriesChangeSort.Request): VideoSeriesChangeSort.Response {
        val userId = LoginHelper.getUserId()!!

        // 解析逗号分隔的 seriesIds 字符串为 List<Long>
        val seriesIdList = request.seriesIds.split(",")
            .map { it.trim().toLong() }

        Mediator.commands.send(
            UpdateCustomerVideoSeriesSortCmd.Request(
                userId = userId,
                seriesIds = seriesIdList
            )
        )

        return VideoSeriesChangeSort.Response()
    }
}
