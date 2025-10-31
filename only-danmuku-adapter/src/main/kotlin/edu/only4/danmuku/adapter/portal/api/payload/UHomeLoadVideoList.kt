package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.OrderInfo
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.domain._share.meta.video.SVideo

/**
 * 加载用户视频列表接口载荷
 */
object UHomeLoadVideoList {

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

    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var createTime: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null
    )
}
