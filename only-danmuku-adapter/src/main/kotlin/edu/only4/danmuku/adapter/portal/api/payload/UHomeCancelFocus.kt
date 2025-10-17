package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 取消关注接口载荷
 */
object UHomeCancelFocus {

    data class Request(
        /** 被取消关注用户ID */

        @field:NotEmpty(message = "被取消关注用户ID不能为空")
        val focusUserId: String = ""
    )

    class Response
}
