package edu.only4.danmuku.adapter.portal.api.payload.video

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video.GetVideoPageQry
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

object GetVideoPage {

    data class Request(
        val parentCategoryId: Long?,
        val categoryId: Long?,
    ): PageParam()

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
        var danmukuCount: Int,
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

    @Mapper(componentModel = "default",
        imports = [RecommendType::class]
    )
    interface Converter {

        @Mapping(target = "categoryParentId", source = "request.parentCategoryId")
        @Mapping(target = "recommendType", expression = "java((request.getCategoryId() == null && request.getParentCategoryId() == null) ? RecommendType.NOT_RECOMMEND : null)")
        fun toQry(request: Request): GetVideoPageQry.Request

        fun fromQry(resp: GetVideoPageQry.Response): Item

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
