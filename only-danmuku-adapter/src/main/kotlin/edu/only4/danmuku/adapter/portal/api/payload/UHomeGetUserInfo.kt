package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取用户信息接口载荷
 */
object UHomeGetUserInfo {

    data class Request(
        /** 用户ID */

        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = null
    )

    data class Response(
        /** 用户信息对象 */
        var userInfo: Map<String, Any>? = null
    )
}
