package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotNull

/**
 * 删除评论接口载荷
 */
object AdminInteractDelComment {

    data class Request(
        /** 评论ID */
        @field:NotNull(message = "评论ID不能为空")
        val commentId: Long? = null
    )

    class Response
}
