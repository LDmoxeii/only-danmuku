package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import edu.only4.danmuku.application.queries.video.GetRecommendVideosQry
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 加载推荐视频接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoLoadRecommend
 */
object VideoLoadRecommend {

    /**
     * 响应结果 - 返回推荐视频列表
     */
    data class Response(
        /** 推荐视频列表 */
        var list: List<VideoItem>? = null
    )

    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd")
        var createTime: Long
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(resp: GetRecommendVideosQry.Response): VideoItem

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
