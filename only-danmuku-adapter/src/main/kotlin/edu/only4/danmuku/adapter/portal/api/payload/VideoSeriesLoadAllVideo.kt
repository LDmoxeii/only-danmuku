package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import edu.only4.danmuku.application.queries.video.GetVideoAllList
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 加载所有视频(用于添加到系列)接口载荷
 */
object VideoSeriesLoadAllVideo {

    data class Request(
        /** 系列ID(排除已在系列中的视频) */
        val seriesId: Int? = null
    )

    data class Response(
        /** 视频列表 */
        var list: List<VideoItem>? = null
    )

    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var playCount: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(v: GetVideoAllList.VideoItem): VideoItem

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
