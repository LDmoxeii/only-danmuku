package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import jakarta.validation.constraints.NotEmpty

/**
 * 搜索视频接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoSearch
 */
object VideoSearch {

    /**
     * 请求参数
     */
    data class Request(
        @field:NotEmpty(message = "搜索关键词不能为空")
        val keyword: String = "",
        val orderType: Int? = null,
    ) : PageParam()

    data class VideoItem(
        var videoId: Long,
        var videoCover: String?,
        var videoName: String?,
        var userId: Long?,
        var createTime: String?,
        var lastUpdateTime: String?,
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
        var lastPlayTime: String?,
        var nickName: String? = null,
        var avatar: String? = null,
        var categoryFullName: String?,
    )
}
