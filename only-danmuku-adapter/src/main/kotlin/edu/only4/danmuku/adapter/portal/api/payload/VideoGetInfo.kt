package edu.only4.danmuku.adapter.portal.api.payload

object VideoGetInfo {

    data class Response(
        var videoInfo: VideoInfo? = null,
        var userActionList: List<UserAction>? = null
    )

    data class VideoInfo(
        var videoId: Long,
        var videoCover: String,
        var videoName: String,
        var userId: Long,
        var createTime: String,
        var postType: Int,
        var originInfo: String?,
        var tags: String?,
        var introduction: String? = null,
        var interaction: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null,
        var coinCount: Int? = null,
        var collectCount: Int? = null
    )

    data class UserAction(
        var actionId: Long,
        var userId: Long,
        var videoId: Long,
        var videoName: String,
        var videoCover: String,
        var videoUserId: Long,
        var commentId: Long?,
        var actionType: Int? = null,
        var actionCount: Int? = null,
        var cationTime: String,
    )
}
