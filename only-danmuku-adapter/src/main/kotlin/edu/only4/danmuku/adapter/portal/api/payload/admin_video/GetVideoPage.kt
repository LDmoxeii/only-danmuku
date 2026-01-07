package edu.only4.danmuku.adapter.portal.api.payload.admin_video

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.adapter.domain.translation.video_post.VideoStatusTranslation
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostPageQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载视频列表(分页)接口载荷
 */
object GetVideoPage {

    class Request(
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String?,
        /** 父分类Id */
        val categoryParentId: Long?,
        /** 分类Id */
        val categoryId: Long?,
        /** 视频状态 */
        val recommendType: Int?,
    ) : PageParam()

    /**
     * 视频项
     */
    data class Item(
        var videoId: String?,
        var videoCover: String?,
        var videoName: String?,
        var userId: String?,
        var nickName: String?,
        var duration: Int?,
        var postType: Int?,
        var originInfo: String?,
        var tags: String?,
        var introduction: String?,
        var status: Int,
        @get:Translation(type = VideoStatusTranslation.VIDEO_STATUS_CODE_TO_DESC, mapper = "status")
        var statusName: String? = null,
        @get:Translation(EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long,
        @get:Translation(EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var lastUpdateTime: Long?,
        var playCount: Int?,
        var likeCount: Int?,
        var danmuCount: Int?,
        var commentCount: Int?,
        var coinCount: Int?,
        var collectCount: Int?,
        var recommendType: Int?,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(target = "recommendType", expression = "java(edu.only4.danmuku.domain.aggregates.video.enums.RecommendType.valueOfOrNull(request.getRecommendType()))")
        fun toQry(request: Request): GetVideoPostPageQry.Request

        @Mapping(source = "videoId", target = "videoId")
        @Mapping(source = "userId", target = "userId")
        fun fromApp(resp: GetVideoPostPageQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
