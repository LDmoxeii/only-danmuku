package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载推荐视频接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoLoadRecommend
 */
object VideoLoadRecommend {

    /**
     * 响应结果 - 返回推荐视频列表
     */
    data class Response(
        /** 推荐视频列表 */
        var list: List<VideoItem>? = null
    )

    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var createTime: String? = null
    )
}
