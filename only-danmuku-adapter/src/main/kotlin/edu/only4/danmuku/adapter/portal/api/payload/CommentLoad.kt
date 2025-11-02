package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageData
import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载评论列表接口载荷
 */
object CommentLoad {

    data class Request(
        val videoId: Long,
    ) : PageParam()

    /**
     * 评论项
     */
    data class Response(
        /** 评论ID */
        var commentId: String? = null,
        /** 父评论ID */
        var pCommentId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 视频用户ID */
        var videoUserId: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 评论内容 */
        var content: String? = null,
        /** 图片路径 */
        var imgPath: String? = null,
        /** 用户ID */
        var userId: String? = null,
        /** 用户昵称 */
        var nickName: String? = null,
        /** 用户头像 */
        var avatar: String? = null,
        /** 点赞数 */
        var likeCount: Int? = null,
        /** 讨厌数 */
        var hateCount: Int? = null,
        /** 是否点赞 */
        var haveLike: Int? = null,
        /** 是否置顶 */
        var topType: Int? = null,
        /** 回复用户ID */
        var replyUserId: String? = null,
        /** 回复用户昵称 */
        var replyNickName: String? = null,
        /** 回复用户头像 */
        var replyAvatar: String? = null,
        /** 发布时间 */
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var postTime: Long,
        /** 子评论数量 */
        var childrenCount: Int? = null,
        /** 子评论列表 */
        var children: List<Response>? = null,
    )

    /**
     * 评论加载响应（包含评论分页数据与用户行为列表）
     */
    data class Result(
        var commentData: PageData<Response>,
        var userActionList: List<UserAction>? = null
    )

    /**
     * 用户行为（该请求专用）
     */
    data class UserAction(
        var actionId: Long,
        var userId: Long,
        var videoId: Long,
        var videoName: String,
        var videoCover: String,
        var videoUserId: Long,
        var commentId: Long?,
        var actionType: Int?,
        var actionCount: Int?,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var cationTime: Long,
    )
}
