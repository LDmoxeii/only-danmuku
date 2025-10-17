package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 关注用户接口载荷
 */
object UHomeFocus {

    data class Request(
        /** 被关注用户ID */

        @field:NotEmpty(message = "被关注用户ID不能为空")
        val focusUserId: String = ""
    )

    class Response
}
