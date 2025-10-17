package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 保存系统设置接口载荷
 */
object AdminSettingSave {

    data class Request(
        /** 系统设置对象 */

        @field:NotEmpty(message = "系统设置对象不能为空")
        val sysSettingDto: Map<String, Any> = null
    )

    class Response
}
