package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * 发表评论接口载荷
 */
object CommentPost {

    data class Request(
        /** 视频ID */
        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = "",
        /** 回复评论ID */
        val replyCommentId: String? = null,
        /** 评论内容 */
        @field:NotEmpty(message = "评论内容不能为空")
        @field:Size(max = 500, message = "长度不能超过500个字符")
        val content: String = "",
        /** 图片路径 */
        @field:Size(max = 50, message = "长度不能超过50个字符")
        val imgPath: String? = null
    )

    data class Response(
        /** 评论对象 */
        var comment: CommentItem? = null
    )

    /**
     * 评论项
     */
    data class CommentItem(
        /** 评论ID */
        var commentId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 用户ID */
        var userId: String? = null,
        /** 评论内容 */
        var content: String? = null,
        /** 图片路径 */
        var imgPath: String? = null,
        /** 发布时间 */
        var postTime: String? = null
    )
}
