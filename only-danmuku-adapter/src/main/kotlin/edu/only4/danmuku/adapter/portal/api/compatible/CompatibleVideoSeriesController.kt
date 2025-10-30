package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.VideoSeriesChangeSort
import edu.only4.danmuku.adapter.portal.api.payload.VideoSeriesLoad
import edu.only4.danmuku.application.commands.customer_video_series.DeleteVideoSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.RemoveVideoFromSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.UpdateVideoSeriesSortCmd
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesVideoQry
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

    @PostMapping("/loadVideoSeries")
    fun getVideoSeries(
        userId: Long,
    ): VideoSeriesLoad.Response {
        // 调用查询获取用户所有系列
        val seriesList = Mediator.queries.send(
            GetCustomerVideoSeriesListQry.Request(userId = userId)
        )

        return VideoSeriesLoad.Response(
            list = seriesList.map { series ->
                VideoSeriesLoad.SeriesItem(
                    seriesId = series.seriesId.toString(),
                    seriesName = series.seriesName,
                    seriesDescription = series.seriesDescription,
                    sort = series.sort,
                    videoCount = series.videoCount,
                    createTime = series.createTime
                )
            }
        )
    }

    @PostMapping("/loadVideoSeriesWithVideo")
    fun loadVideoSeriesWithVideo(
        userId: String?,
    ): List<Map<String, Any?>> {
        // 允许未传 userId 时，默认使用当前登录用户
        val actualUserId = (userId?.takeIf { it.isNotBlank() }?.toLong())
            ?: LoginHelper.getUserId()
            ?: 0L

        if (actualUserId == 0L) {
            return emptyList()
        }

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
        seriesId: Long?,
        @NotEmpty @Size(max= 100) seriesName: String,
        @Size(max = 200) seriesDescription: String?,
        videoIds: String?,
    ) {
        val userId = LoginHelper.getUserId()!!

        // TODO 需要根据传入ID获取系列信息
//        Mediator.commands.send(
//            UpdateCustomerVideoSeriesVideosCmd.Request(
//                userId = userId,
//                seriesId = request.seriesId.toLong(),
//                videoIds = request.videoIds,
//                isDelete = false
//            )
//        )
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


