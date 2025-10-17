package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 修改用户状态接口载荷
 */
object AdminUserChangeStatus {

    data class Request(
        /** 用户ID */

        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = "",
        /** 新状态 */

        @field:NotEmpty(message = "新状态不能为空")
        val status: Int = 0
    )

    class Response
}
