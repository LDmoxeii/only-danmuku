package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import jakarta.validation.constraints.NotEmpty

/**
 * 加载用户视频列表接口载荷
 */
object UHomeLoadVideoList {

    data class Request(
        /** 用户ID */
        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = "",
        /** 类型 */
        val type: Int? = null,
        /** 视频名称 */
        val videoName: String? = null,
        /** 排序类型 */
        val orderType: Int? = null
    ) : PageParam()

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
