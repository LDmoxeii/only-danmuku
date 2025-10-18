package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import jakarta.validation.constraints.NotEmpty

/**
 * 加载用户收藏列表接口载荷
 */
object UHomeLoadUserCollection {

    data class Request(
        /** 用户ID */
        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = ""
    ) : PageParam()

    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var collectTime: String? = null
    )
}
