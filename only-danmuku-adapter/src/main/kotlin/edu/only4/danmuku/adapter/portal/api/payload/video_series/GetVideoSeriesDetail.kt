package edu.only4.danmuku.adapter.portal.api.payload.video_series

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation

object GetVideoSeriesDetail {

    data class Request(
        val seriesId: Long
    )

    data class Response(
        var videoSeries: VideoSeries? = null,
        var seriesVideoList: List<SeriesVideoItem>? = null
    )

    data class VideoSeries(
        var seriesId: String? = null,
        var userId: String? = null,
        var seriesName: String? = null,
        var seriesDescription: String? = null,
        var sort: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var updateTime: Long? = null,
    )

    data class SeriesVideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var playCount: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long? = null,
    )
}
