package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取资源文件接口载荷
 */
object AdminFileGetResource {

    data class Request(
        /** 资源文件名 */

        @field:NotEmpty(message = "资源文件名不能为空")
        val sourceName: String = null
    )

    class Response
}
