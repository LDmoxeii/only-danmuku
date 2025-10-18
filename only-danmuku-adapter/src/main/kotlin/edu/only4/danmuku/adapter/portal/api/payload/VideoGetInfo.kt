package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频详情接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoGetInfo
 */
object VideoGetInfo {

    /**
     * 请求参数
     */
    data class Request(
        /** 视频ID */
        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = ""
    )

    /**
     * 响应结果 - 视频详情和用户行为
     */
    data class Response(
        /** 视频信息 */
        var videoInfo: VideoInfo? = null,
        /** 用户行为列表 */
        var userActionList: List<UserAction>? = null
    )

    data class VideoInfo(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var introduction: String? = null,
        var interaction: String? = null,
        var duration: Int? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null,
        var coinCount: Int? = null,
        var collectCount: Int? = null,
        var createTime: String? = null
    )

    data class UserAction(
        var actionType: Int? = null,
        var actionCount: Int? = null
    )
}
