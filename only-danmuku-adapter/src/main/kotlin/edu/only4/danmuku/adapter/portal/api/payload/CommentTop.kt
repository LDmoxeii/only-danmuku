package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 置顶评论接口载荷
 */
object CommentTop {

    data class Request(
        /** 评论ID */

        @field:NotEmpty(message = "评论ID不能为空")
        val commentId: Int = null
    )

    class Response
}
