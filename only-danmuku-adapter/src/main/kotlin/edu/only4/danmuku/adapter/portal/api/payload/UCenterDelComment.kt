package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除评论接口载荷
 */
object UCenterDelComment {

    data class Request(
        /** 评论ID */

        @field:NotEmpty(message = "评论ID不能为空")
        val commentId: Int = null
    )

    class Response
}
