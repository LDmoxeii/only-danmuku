package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_series.ChangeVideoSeriesSort
import edu.only4.danmuku.adapter.portal.api.payload.video_series.DeleteSeriesVideo
import edu.only4.danmuku.adapter.portal.api.payload.video_series.DeleteVideoSeries
import edu.only4.danmuku.adapter.portal.api.payload.video_series.VideoSeriesLoad
import edu.only4.danmuku.adapter.portal.api.payload.video_series.GetAllSeriesVideoList
import edu.only4.danmuku.adapter.portal.api.payload.video_series.GetVideoSeriesDetail
import edu.only4.danmuku.adapter.portal.api.payload.video_series.LoadVideoSeriesWithVideo
import edu.only4.danmuku.adapter.portal.api.payload.video_series.SaveSeriesVideo
import edu.only4.danmuku.adapter.portal.api.payload.video_series.SaveVideoSeries
import edu.only4.danmuku.application.commands.customer_video_series.*
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesInfoQry
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesVideoQry
import edu.only4.danmuku.application.queries.video.GetVideoAllList
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uHome/series")
@Validated
class VideoSeriesController {

    @SaIgnore
    @PostMapping("/getVideoSeries")
    fun getVideoSeries(@RequestBody @Validated request: VideoSeriesLoad.Request): VideoSeriesLoad.Response {
        // 调用查询获取用户所有系列
        val actualUserId = request.userId ?: LoginHelper.getUserId()!!

        val seriesList = Mediator.queries.send(
            GetCustomerVideoSeriesListQry.Request(userId = actualUserId)
        )

        return VideoSeriesLoad.Response(
            list = seriesList.map { VideoSeriesLoad.Converter.INSTANCE.fromApp(it) }
        )
    }

    @SaIgnore
    @PostMapping("/detail")
    fun detail(@RequestBody @Validated request: GetVideoSeriesDetail.Request): GetVideoSeriesDetail.Response {
        val detail = Mediator.queries.send(
            GetCustomerVideoSeriesInfoQry.Request(seriesId = request.seriesId)
        )

        val videoSeries = GetVideoSeriesDetail.VideoSeries(
            seriesId = detail.seriesId.toString(),
            userId = detail.userId.toString(),
            seriesName = detail.seriesName,
            seriesDescription = detail.seriesDescription,
            sort = detail.sort,
            updateTime = detail.updateTime ?: detail.createTime
        )

        val seriesVideoList = detail.videoList?.map { v ->
            GetVideoSeriesDetail.SeriesVideoItem(
                videoId = v.videoId.toString(),
                videoCover = v.videoCover,
                videoName = v.videoName,
                playCount = v.playCount,
                createTime = v.createTime
            )
        } ?: emptyList()

        return GetVideoSeriesDetail.Response(
            videoSeries = videoSeries,
            seriesVideoList = seriesVideoList
        )
    }

    @PostMapping("/getAllVideoList")
    fun getAllVideoList(@RequestBody @Validated request: GetAllSeriesVideoList.Request): GetAllSeriesVideoList.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 查询当前用户的所有视频
        val allVideos = Mediator.queries.send(
            GetVideoAllList.Request(
                userId = currentUserId,
            )
        )

        // 如果传了系列ID，则需要把该系列下已存在的视频排除
        val excludeVideoIds: Set<Long> = if (request.seriesId != null) {
            val seriesWithVideos = Mediator.queries.send(
                GetCustomerVideoSeriesVideoQry.Request(userId = currentUserId)
            )
            seriesWithVideos
                .firstOrNull { it.seriesId == request.seriesId }
                ?.videoList
                ?.map { it.videoId }
                ?.toSet()
                ?: emptySet()
        } else emptySet()

        val filtered = if (excludeVideoIds.isEmpty()) allVideos else allVideos.filter { it.videoId !in excludeVideoIds }

        return GetAllSeriesVideoList.Response(
            list = filtered.map { v -> GetAllSeriesVideoList.Converter.INSTANCE.fromApp(v) }
        )
    }

    @PostMapping("/saveVideoSeries")
    fun saveVideoSeries(@RequestBody @Validated request: SaveVideoSeries.Request): SaveVideoSeries.Response {
        val userId = LoginHelper.getUserId()!!

        val createdOrUpdatedSeriesId = if (request.seriesId == null) {
            val resp = Mediator.commands.send(
                CreateCustomerVideoSeriesCmd.Request(
                    userId = userId,
                    seriesId = null,
                    seriesName = request.seriesName,
                    seriesDescription = request.seriesDescription,
                    videoIds = request.videoIds
                )
            )
            resp.seriesId
        } else {
            // 先更新基础信息
            Mediator.commands.send(
                UpdateCustomerVideoSeriesInfoCmd.Request(
                    userId = userId,
                    seriesId = request.seriesId,
                    seriesName = request.seriesName,
                    seriesDescription = request.seriesDescription
                )
            )
            // 有视频列表时再替换/追加（命令内部去重，并限制最大数量）
            if (!request.videoIds.isNullOrBlank()) {
                Mediator.commands.send(
                    UpdateCustomerVideoSeriesVideosCmd.Request(
                        userId = userId,
                        seriesId = request.seriesId,
                        videoIds = request.videoIds,
                        isDelete = false
                    )
                )
            }
            request.seriesId
        }

        return SaveVideoSeries.Response(seriesId = createdOrUpdatedSeriesId)
    }

    @SaIgnore
    @PostMapping("/loadVideoSeriesWithVideo")
    fun loadVideoSeriesWithVideo(
        @RequestBody @Validated request: LoadVideoSeriesWithVideo.Request
    ): LoadVideoSeriesWithVideo.Response {
        val actualUserId = request.userId ?: LoginHelper.getUserId()!!

        val seriesWithVideos = Mediator.queries.send(
            GetCustomerVideoSeriesVideoQry.Request(userId = actualUserId)
        )

        // 适配前端所需字段：videoInfoList
        val list = seriesWithVideos.map { series ->
            LoadVideoSeriesWithVideo.Item(
                seriesId = series.seriesId.toString(),
                seriesName = series.seriesName,
                seriesDescription = series.seriesDescription,
                sort = series.sort,
                videoInfoList = series.videoList?.map { v ->
                    LoadVideoSeriesWithVideo.VideoInfoItem(
                        videoId = v.videoId.toString(),
                        videoCover = v.videoCover,
                        videoName = v.videoName,
                        playCount = v.playCount
                    )
                } ?: emptyList()
            )
        }

        return LoadVideoSeriesWithVideo.Response(list = list)
    }

    @PostMapping("/saveSeriesVideo")
    fun saveSeriesVideo(@RequestBody @Validated request: SaveSeriesVideo.Request): SaveSeriesVideo.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            UpdateCustomerVideoSeriesVideosCmd.Request(
                userId = userId,
                seriesId = request.seriesId,
                videoIds = request.videoIds,
                isDelete = false
            )
        )

        return SaveSeriesVideo.Response()
    }

    @PostMapping("/delSeriesVideo")
    fun deleteSeriesVideo(@RequestBody @Validated request: DeleteSeriesVideo.Request): DeleteSeriesVideo.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            RemoveVideoFromSeriesCmd.Request(
                seriesId = request.seriesId,
                videoId = request.videoId,
                operatorId = userId
            )
        )

        return DeleteSeriesVideo.Response()
    }

    @PostMapping("/delVideoSeries")
    fun deleteVideoSeries(@RequestBody @Validated request: DeleteVideoSeries.Request): DeleteVideoSeries.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            DeleteVideoSeriesCmd.Request(
                userId = userId,
                seriesId = request.seriesId
            )
        )

        return DeleteVideoSeries.Response()
    }

    @PostMapping("/changeSort")
    fun changeSort(@RequestBody @Validated request: ChangeVideoSeriesSort.Request): ChangeVideoSeriesSort.Response {
        val userId = LoginHelper.getUserId()!!

        val seriesIdList = request.seriesIds.split(",")
            .map { it.trim().toLong() }

        Mediator.commands.send(
            UpdateVideoSeriesSortCmd.Request(
                userId = userId,
                seriesIds = seriesIdList
            )
        )

        return ChangeVideoSeriesSort.Response()
    }
}


