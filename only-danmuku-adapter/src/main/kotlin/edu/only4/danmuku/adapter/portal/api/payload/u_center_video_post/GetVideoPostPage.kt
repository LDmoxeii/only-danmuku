package edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.adapter.domain.translation.video_post.VideoStatusTranslation
import edu.only4.danmuku.application.queries.video_draft.GetUserVideoPostQry
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

object GetVideoPostPage {

    data class Request(
        val status: VideoStatus? = null,
        val videoNameFuzzy: String? = null
    ) : PageParam()

    data class Item(
        val videoPostId: Long,
        var videoId: Long?,
        var videoCover: String? = null,
        var videoName: String? = null,
        var duration: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var lastUpdateTime: Long? = null,
        var status: Int? = null,
        @get:Translation(type = VideoStatusTranslation.VIDEO_STATUS_CODE_TO_DESC, mapper = "status")
        var statusName: String? = null,
        var interaction: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null,
        var coinCount: Int? = null,
        var collectCount: Int? = null,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(resp: GetUserVideoPostQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
