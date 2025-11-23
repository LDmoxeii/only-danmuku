package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video_danmuku.GetVideoDanmukuPageQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载用户收到的弹幕接口载荷
 */
object UCenterLoadDanmu {

    data class Request(
        val videoId: String? = null
    ) : PageParam()

    /**
     * 弹幕项
     */
    data class DanmukuItem(
        var danmukuId: Long,
        var videoId: Long,
        var videoName: String,
        var text: String,
        var userId: Long,
        var nickName: String,
        var mode: Int,
        var color: String,
        var time: Int,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var postTime: Long,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "customerId", target = "userId")
        @Mapping(source = "customerNickname", target = "nickName")
        fun fromApp(resp: GetVideoDanmukuPageQry.Response): DanmukuItem

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
