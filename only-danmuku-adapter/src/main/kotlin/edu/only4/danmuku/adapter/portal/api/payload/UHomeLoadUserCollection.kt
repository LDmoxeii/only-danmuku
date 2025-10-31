package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载用户收藏列表接口载荷
 */
object UHomeLoadUserCollection {

    data class Request(
        val userId: Long?
    ) : PageParam()

    data class VideoItem(
        var actionId: Long,
        var videoId: String,
        var videoUserId: String,
        var commentId: Long,
        var actionType: Int,
        var actionCount: Int,
        var userId: String,
        var actionTime: String,
        var videoName: String,
        var videoCover: String,
    )
}
