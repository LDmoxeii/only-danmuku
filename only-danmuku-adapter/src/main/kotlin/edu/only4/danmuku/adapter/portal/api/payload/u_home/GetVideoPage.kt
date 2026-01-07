package edu.only4.danmuku.adapter.portal.api.payload.u_home

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.OrderInfo
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video.GetVideoPageQry
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载用户视频列表接口载荷
 */
object GetVideoPage {

    data class Request(
        val userId: Long,
        val type: Int?,
        val videoName: String?,
        val orderType: Int?
    ) : PageParam() {
        companion object {
            val CREATE_TIME_DESC = OrderInfo.desc(SVideo.props.createTime)
            val PLAY_COUNT_DESC = OrderInfo.desc(SVideo.props.playCount)
            val COLLECT_COUNT_DESC = OrderInfo.desc(SVideo.props.collectCount)
        }
    }

    data class Item(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "videoId", target = "videoId")
        fun fromApp(resp: GetVideoPageQry.Response): Item

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
