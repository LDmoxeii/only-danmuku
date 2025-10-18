package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.customer_video_series.*
import edu.only4.danmuku.application.queries.customer_video_series.*
import edu.only4.danmuku.application.queries.video.SearchVideosQry
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
 * 视频系列控制器 - 处理视频系列管理操作
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
                        Instant.ofEpochMilli(series.createTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
            }
        )
    }

    /**
     * 保存视频系列
     */
    @PostMapping("/saveVideoSeries")
    fun videoSeriesSave(@RequestBody @Validated request: VideoSeriesSave.Request): VideoSeriesSave.Response {
        // TODO: 从上下文获取当前用户ID
        val userId = 1L // 临时硬编码

        // 调用命令创建或更新系列
        Mediator.commands.send(
            CreateCustomerVideoSeriesCmd.Request(
                userId = userId,
                seriesId = request.seriesId?.toLong(),
                seriesName = request.seriesName,
                seriesDescription = request.seriesDescription,
                videoIds = request.videoIds
            )
        )

        return VideoSeriesSave.Response()
    }

    /**
     * 加载所有视频(用于添加到系列)
     */
    @PostMapping("/loadAllVideo")
    fun videoSeriesLoadAllVideo(@RequestBody request: VideoSeriesLoadAllVideo.Request): VideoSeriesLoadAllVideo.Response {
        // TODO: 从上下文获取当前用户ID
        val userId = 1L // 临时硬编码

        // 如果提供了seriesId，需要排除已在该系列中的视频
        // TODO: 实现查询用户所有视频，排除已在系列中的视频
        // 目前使用SearchVideosQry作为临时实现
        val queryRequest = SearchVideosQry.Request(
            videoNameFuzzy = null,
            status = null
        )

        val queryResult = Mediator.queries.send(queryRequest)

        return VideoSeriesLoadAllVideo.Response(
            list = queryResult.list.map { video ->
                VideoSeriesLoadAllVideo.VideoItem(
                    videoId = video.videoId.toString(),
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    playCount = video.playCount
                )
            }
        )
    }

    /**
     * 获取系列详情
     */
    @PostMapping("/getVideoSeriesDetail")
    fun videoSeriesGetDetail(@RequestBody @Validated request: VideoSeriesGetDetail.Request): VideoSeriesGetDetail.Response {
        // 调用查询获取系列详情(包含视频列表)
        val seriesInfo = Mediator.queries.send(
            GetCustomerVideoSeriesInfoQry.Request(seriesId = request.seriesId.toLong())
        )

        return VideoSeriesGetDetail.Response(
            seriesInfo = VideoSeriesGetDetail.SeriesInfo(
                seriesId = seriesInfo.seriesId.toString(),
                userId = seriesInfo.userId.toString(),
                seriesName = seriesInfo.seriesName,
                seriesDescription = seriesInfo.seriesDescription,
                sort = seriesInfo.sort,
                createTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(seriesInfo.createTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ),
            videoList = seriesInfo.videoList?.map { video ->
                VideoSeriesGetDetail.VideoItem(
                    videoId = video.videoId.toString(),
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    playCount = video.playCount,
                    sort = video.sort
                )
            }
        )
    }

    /**
     * 保存系列视频
     */
    @PostMapping("/saveSeriesVideo")
    fun videoSeriesSaveVideo(@RequestBody @Validated request: VideoSeriesSaveVideo.Request): VideoSeriesSaveVideo.Response {
        // TODO: 从上下文获取当前用户ID
        val userId = 1L // 临时硬编码

        // 调用命令添加视频到系列
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
        // TODO: 从上下文获取当前用户ID
        val userId = 1L // 临时硬编码

        // 调用命令从系列中删除视频
        Mediator.commands.send(
            UpdateCustomerVideoSeriesVideosCmd.Request(
                userId = userId,
                seriesId = request.seriesId.toLong(),
                videoIds = request.videoId,
                isDelete = true
            )
        )

        return VideoSeriesDelVideo.Response()
    }

    /**
     * 删除视频系列
     */
    @PostMapping("/delVideoSeries")
    fun videoSeriesDel(@RequestBody @Validated request: VideoSeriesDel.Request): VideoSeriesDel.Response {
        // TODO: 从上下文获取当前用户ID
        val userId = 1L // 临时硬编码

        // 调用命令删除系列
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
        // TODO: 从上下文获取当前用户ID
        val userId = 1L // 临时硬编码

        // 调用命令更新系列排序
        Mediator.commands.send(
            UpdateCustomerVideoSeriesSortCmd.Request(
                userId = userId,
                seriesIds = request.seriesIds
            )
        )

        return VideoSeriesChangeSort.Response()
    }

    /**
     * 加载系列及关联视频
     */
    @PostMapping("/loadVideoSeriesWithVideo")
    fun videoSeriesLoadWithVideo(@RequestBody @Validated request: VideoSeriesLoadWithVideo.Request): VideoSeriesLoadWithVideo.Response {
        // TODO: 实现加载系列及关联视频逻辑
        // 需要创建专门的查询来获取系列及其关联的视频列表
        // 目前返回空列表
        return VideoSeriesLoadWithVideo.Response(list = emptyList())
    }

}
