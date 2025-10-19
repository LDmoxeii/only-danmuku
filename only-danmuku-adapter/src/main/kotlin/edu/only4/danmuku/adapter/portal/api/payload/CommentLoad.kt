package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import jakarta.validation.constraints.NotEmpty

/**
 * 加载评论列表接口载荷
 */
object CommentLoad {

    data class Request(
        /** 视频ID */
        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = "",
        /** 排序类型 */
        val orderType: Int? = null
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
        /** 是否点赞 */
        var haveLike: Int? = null,
        /** 是否置顶 */
        var topType: Int? = null,
        /** 发布时间 */
        var postTime: String? = null,
        /** 子评论数量 */
        var childrenCount: Int? = null,
        /** 子评论列表 */
        var children: List<Response>? = null,
    )
}
