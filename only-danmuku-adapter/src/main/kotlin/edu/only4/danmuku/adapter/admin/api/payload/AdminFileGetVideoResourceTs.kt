package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频TS分片接口载荷
 */
object AdminFileGetVideoResourceTs {

    data class Request(
        /** 文件ID */

        @field:NotEmpty(message = "文件ID不能为空")
        val fileId: String = "",
        /** TS文件名 */

        @field:NotEmpty(message = "TS文件名不能为空")
        val ts: String = ""
    )

    class Response
}
