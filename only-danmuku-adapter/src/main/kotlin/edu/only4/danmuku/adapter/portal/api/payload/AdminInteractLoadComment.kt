package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import java.time.LocalDateTime

/**
 * 加载评论列表(分页)接口载荷
 */
object AdminInteractLoadComment {

    class Request(
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null
    ) : PageParam()

    /**
     * 评论项
     */
    data class Response(
        /** 评论ID */
        var commentId: Long? = null,
        /** 父评论ID */
        var pCommentId: Long? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 用户ID */
        var userId: String? = null,
        /** 用户昵称 */
        var nickName: String? = null,
        /** 用户头像 */
        var avatar: String? = null,
        /** 回复用户ID */
        var replyUserId: String? = null,
        /** 回复用户昵称 */
        var replyNickName: String? = null,
        /** 评论内容 */
        var content: String? = null,
        /** 评论图片路径 */
        var imgPath: String? = null,
        /** 置顶类型: 0-未置顶 1-置顶 */
        var topType: Int? = null,
        /** 点赞数 */
        var likeCount: Int? = null,
        /** 讨厌数 */
        var hateCount: Int? = null,
        /** 发布时间 */
        var postTime: LocalDateTime? = null
    )
}
