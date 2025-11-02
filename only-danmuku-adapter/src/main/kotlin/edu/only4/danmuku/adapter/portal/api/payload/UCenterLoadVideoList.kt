package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import com.only.engine.translation.annotation.Translation
import edu.only4.danmuku.adapter.domain.translation.video_post.VideoStatusTranslation.Companion.VIDEO_STATUS_CODE_TO_DESC

object UCenterLoadVideoList {

    data class Request(
        val status: Int? = null,
        val videoNameFuzzy: String? = null
    ) : PageParam()

    data class VideoItem(
        val videoPostId: Long,
        var videoId: Long?,
        var videoCover: String? = null,
        var videoName: String? = null,
        var duration: Int? = null,
        var createTime: String? = null,
        var lastUpdateTime: String? = null,
        var status: Int? = null,
        @get:Translation(type = VIDEO_STATUS_CODE_TO_DESC, mapper = "status")
        var statusName: String? = null,
        var interaction: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null,
        var coinCount: Int? = null,
        var collectCount: Int? = null,
    )
}
