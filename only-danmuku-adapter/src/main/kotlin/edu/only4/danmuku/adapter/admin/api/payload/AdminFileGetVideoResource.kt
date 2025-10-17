package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频资源(m3u8)接口载荷
 */
object AdminFileGetVideoResource {

    data class Request(
        /** 文件ID */

        @field:NotEmpty(message = "文件ID不能为空")
        val fileId: String = ""
    )

    class Response
}
