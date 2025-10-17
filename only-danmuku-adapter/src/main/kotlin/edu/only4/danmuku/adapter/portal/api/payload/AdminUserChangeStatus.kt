package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

/**
 * 修改用户状态接口载荷
 */
object AdminUserChangeStatus {

    data class Request(
        /** 用户ID */
        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String? = null,

        /** 新状态: 0-禁用 1-正常 */
        @field:NotNull(message = "新状态不能为空")
        val status: Int? = null
    )

    class Response
}
