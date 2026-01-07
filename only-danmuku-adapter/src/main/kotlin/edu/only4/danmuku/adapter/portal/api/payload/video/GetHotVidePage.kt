package edu.only4.danmuku.adapter.portal.api.payload.video

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video.GetHotVideoPageQry
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

object GetHotVidePage {

    /**
     * 请求参数
     */
    class Request: PageParam()

    data class Item(
        var videoId: Long,
        var videoCover: String?,
        var videoName: String?,
        var userId: Long?,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var lastUpdateTime: Long?,
        var parentCategoryId: Long,
        var categoryId: Long?,
        var postType: Int,
        var originInfo: String?,
        var tags: String?,
        var introduction: String?,
        var duration: Int,
        var playCount: Int,
        var likeCount: Int,
        var danmuCount: Int,
        var commentCount: Int,
        var coinCount: Int,
        var collectCount: Int,
        var recommendType: Int,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var lastPlayTime: Long?,
        var nickName: String? = null,
        var avatar: String? = null,
        var categoryFullName: String?,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(resp: GetHotVideoPageQry.Response): Item

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
