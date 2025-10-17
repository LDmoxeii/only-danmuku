package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 用户删除评论接口载荷
 */
object CommentUserDel {

    data class Request(
        /** 评论ID */
        @field:NotEmpty(message = "评论ID不能为空")
        val commentId: String = ""
    )

    class Response()
}
