package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import edu.only4.danmuku.application.queries.video.GetVideoAllList
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 加载所有视频接口载荷
 */
object UCenterLoadAllVideo {

    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(item: GetVideoAllList.VideoItem): VideoItem

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
