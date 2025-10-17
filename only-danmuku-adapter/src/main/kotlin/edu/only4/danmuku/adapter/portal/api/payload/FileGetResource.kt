package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取资源文件接口载荷
 */
object FileGetResource {

    data class Request(
        /** 资源名称 */

        @field:NotEmpty(message = "资源名称不能为空")
        val sourceName: String = null
    )

    class Response
}
