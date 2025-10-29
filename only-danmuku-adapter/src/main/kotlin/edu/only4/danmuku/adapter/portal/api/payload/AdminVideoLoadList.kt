package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.adapter.portal.api.payload.translation.VideoStatusTranslation.Companion.VIDEO_STATUS_CODE_TO_DESC
import java.time.LocalDateTime

/**
 * 加载视频列表(分页)接口载荷
 */
object AdminVideoLoadList {

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
    data class VideoItem(
        var videoId: String?,
        var videoCover: String?,
        var videoName: String?,
        var userId: String?,
        var nickName: String?,
        var duration: Int?,
        var status: Int,
        @get:Translation(type = VIDEO_STATUS_CODE_TO_DESC, mapper = "status")
        var statusName: String? = null,
        var createTime: LocalDateTime?,
        var lastUpdateTime: LocalDateTime?,
        var playCount: Int?,
        var likeCount: Int?,
        var danmuCount: Int?,
        var commentCount: Int?,
        var coinCount: Int?,
        var collectCount: Int?,
        var recommendType: Int?,
    )
}
