package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video_danmuku.GetVideoDanmukuPageQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载弹幕列表(分页)接口载荷
 */
object AdminInteractLoadDanmuku {

    class Request(
        val videoNameFuzzy: String? = null
    ) : PageParam()

    class Response(
        var danmukuId: Long? = null,
        var videoId: String? = null,
        var videoName: String? = null,
        var videoCover: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        var text: String? = null,
        var mode: Int? = null,
        var color: String? = null,
        var time: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var postTime: Long? = null
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun toQry(request: Request): GetVideoDanmukuPageQry.Request

        @Mapping(source = "videoId", target = "videoId")
        @Mapping(source = "customerId", target = "userId")
        @Mapping(source = "customerNickname", target = "nickName")
        fun fromApp(resp: GetVideoDanmukuPageQry.Response): Response

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
