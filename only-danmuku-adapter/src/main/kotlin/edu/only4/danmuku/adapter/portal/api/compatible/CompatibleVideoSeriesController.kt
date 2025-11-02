package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.VideoSeriesChangeSort
import edu.only4.danmuku.adapter.portal.api.payload.VideoSeriesLoad
import edu.only4.danmuku.adapter.portal.api.payload.VideoSeriesLoadAllVideo
import edu.only4.danmuku.adapter.portal.api.payload.VideoSeriesLoad.SeriesItem
import edu.only4.danmuku.application.commands.customer_video_series.DeleteVideoSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.RemoveVideoFromSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.UpdateVideoSeriesSortCmd
import edu.only4.danmuku.application.commands.customer_video_series.CreateCustomerVideoSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.UpdateCustomerVideoSeriesVideosCmd
import edu.only4.danmuku.application.commands.customer_video_series.UpdateCustomerVideoSeriesInfoCmd
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesVideoQry
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesInfoQry
import edu.only4.danmuku.application.queries.video.GetVideoAllList
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uhome/series")
@Validated
class CompatibleVideoSeriesController {

    @SaIgnore
    @PostMapping("/loadVideoSeries")
    fun getVideoSeries(
        userId: Long?,
    ): List<SeriesItem> {
        // 调用查询获取用户所有系列
        val actualUserId = userId ?: LoginHelper.getUserId()!!

        val seriesList = Mediator.queries.send(
            GetCustomerVideoSeriesListQry.Request(userId = actualUserId)
        )

        return seriesList.map { series ->
            SeriesItem(
                seriesId = series.seriesId.toString(),
                seriesName = series.seriesName,
                seriesDescription = series.seriesDescription,
                sort = series.sort,
                videoCount = series.videoCount,
                cover = series.cover,
                createTime = series.createTime,
                updateTime = series.updateTime
            )
        }
    }

    @SaIgnore
    @PostMapping("/getVideoSeriesDetail")
    fun getVideoSeriesDetail(seriesId: Long): Map<String, Any?> {
        val detail = Mediator.queries.send(
            GetCustomerVideoSeriesInfoQry.Request(seriesId = seriesId)
        )

        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val videoSeries = mutableMapOf<String, Any?>(
            "seriesId" to detail.seriesId.toString(),
            "userId" to detail.userId.toString(),
            "seriesName" to detail.seriesName,
            "seriesDescription" to detail.seriesDescription,
            "sort" to detail.sort,
            "updateTime" to java.time.LocalDateTime.ofInstant(
                java.time.Instant.ofEpochSecond(detail.updateTime ?: detail.createTime),
                java.time.ZoneId.systemDefault()
            ).format(formatter)
        )

        val seriesVideoList = detail.videoList?.map { v ->
            mapOf(
                "videoId" to v.videoId.toString(),
                "videoCover" to v.videoCover,
                "videoName" to v.videoName,
                "playCount" to v.playCount,
                "createTime" to (v.createTime?.let { ts ->
                    java.time.LocalDateTime.ofInstant(
                        java.time.Instant.ofEpochSecond(ts),
                        java.time.ZoneId.systemDefault()
                    ).format(formatter)
                })
            )
        } ?: emptyList<Map<String, Any?>>()

        return mapOf(
            "videoSeries" to videoSeries,
            "seriesVideoList" to seriesVideoList
        )
    }

    @PostMapping("/loadAllVideo")
    fun loadAllVideo(
        seriesId: Long?,
    ): List<VideoSeriesLoadAllVideo.VideoItem> {
        val currentUserId = LoginHelper.getUserId()!!

        // 查询当前用户的所有视频
        val allVideos = Mediator.queries.send(
            GetVideoAllList.Request(
                userId = currentUserId,
            )
        )

        // 如果传了系列ID，则需要把该系列下已存在的视频排除
        val excludeVideoIds: Set<Long> = if (seriesId != null) {
            val seriesWithVideos = Mediator.queries.send(
                GetCustomerVideoSeriesVideoQry.Request(userId = currentUserId)
            )
            seriesWithVideos
                .firstOrNull { it.seriesId == seriesId }
                ?.videoList
                ?.map { it.videoId }
                ?.toSet()
                ?: emptySet()
        } else emptySet()

        val filtered = if (excludeVideoIds.isEmpty()) allVideos else allVideos.filter { it.videoId !in excludeVideoIds }

        return filtered.map { v ->
            VideoSeriesLoadAllVideo.VideoItem(
                videoId = v.videoId.toString(),
                videoCover = v.videoCover,
                videoName = v.videoName,
                playCount = v.playCount,
                createTime = java.time.LocalDateTime.ofInstant(
                    java.time.Instant.ofEpochSecond(v.createTime),
                    java.time.ZoneId.systemDefault()
                ).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        }
    }

    @PostMapping("/saveVideoSeries")
    fun saveVideoSeries(
        seriesId: Long?,
        @NotEmpty @Size(max = 100) seriesName: String,
        @Size(max = 200) seriesDescription: String?,
        videoIds: String?,
    ): Map<String, Any?> {
        val userId = LoginHelper.getUserId()!!

        val createdOrUpdatedSeriesId = if (seriesId == null) {
            val resp = Mediator.commands.send(
                CreateCustomerVideoSeriesCmd.Request(
                    userId = userId,
                    seriesId = null,
                    seriesName = seriesName,
                    seriesDescription = seriesDescription,
                    videoIds = videoIds
                )
            )
            resp.seriesId
        } else {
            // 先更新基础信息
            Mediator.commands.send(
                UpdateCustomerVideoSeriesInfoCmd.Request(
                    userId = userId,
                    seriesId = seriesId,
                    seriesName = seriesName,
                    seriesDescription = seriesDescription
                )
            )
            // 有视频列表时再替换/追加（命令内部去重，并限制最大数量）
            if (!videoIds.isNullOrBlank()) {
                Mediator.commands.send(
                    UpdateCustomerVideoSeriesVideosCmd.Request(
                        userId = userId,
                        seriesId = seriesId,
                        videoIds = videoIds,
                        isDelete = false
                    )
                )
            }
            seriesId
        }

        return mapOf("seriesId" to createdOrUpdatedSeriesId)
    }

    @SaIgnore
    @PostMapping("/loadVideoSeriesWithVideo")
    fun loadVideoSeriesWithVideo(
        userId: Long?,
    ): List<Map<String, Any?>> {
        val actualUserId = userId ?: LoginHelper.getUserId()!!

        val seriesWithVideos = Mediator.queries.send(
            GetCustomerVideoSeriesVideoQry.Request(userId = actualUserId)
        )

        // 适配前端所需字段：videoInfoList
        return seriesWithVideos.map { series ->
            mapOf(
                "seriesId" to series.seriesId.toString(),
                "seriesName" to series.seriesName,
                "seriesDescription" to series.seriesDescription,
                "sort" to series.sort,
                "videoInfoList" to (series.videoList?.map { v ->
                    mapOf(
                        "videoId" to v.videoId.toString(),
                        "videoCover" to v.videoCover,
                        "videoName" to v.videoName,
                        "playCount" to v.playCount
                        // createTime 在当前 DTO 不包含，如需请扩展 DTO 后再返回
                    )
                } ?: emptyList<Map<String, Any?>>())
            )
        }
    }

    @PostMapping("/saveSeriesVideo")
    fun saveSeriesVideo(
        seriesId: Long,
        @NotEmpty videoIds: String,
    ) {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            UpdateCustomerVideoSeriesVideosCmd.Request(
                userId = userId,
                seriesId = seriesId!!,
                videoIds = videoIds,
                isDelete = false
            )
        )
    }

    @PostMapping("/delSeriesVideo")
    fun deleteSeriesVideo(
        seriesId: Long,
        videoId: Long,
    ) {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            RemoveVideoFromSeriesCmd.Request(
                seriesId = seriesId,
                videoId = videoId,
                operatorId = userId
            )
        )
    }

    @PostMapping("/delVideoSeries")
    fun deleteVideoSeries(
        seriesId: Long
    ) {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            DeleteVideoSeriesCmd.Request(
                userId = userId,
                seriesId = seriesId
            )
        )
    }

    @PostMapping("/changeVideoSeriesSort")
    fun videoSeriesChangeSort(
        seriesIds: String,
    ): VideoSeriesChangeSort.Response {
        val userId = LoginHelper.getUserId()!!

        val seriesIdList = seriesIds.split(",")
            .map { it.trim().toLong() }

        Mediator.commands.send(
            UpdateVideoSeriesSortCmd.Request(
                userId = userId,
                seriesIds = seriesIdList
            )
        )

        return VideoSeriesChangeSort.Response()
    }
}


