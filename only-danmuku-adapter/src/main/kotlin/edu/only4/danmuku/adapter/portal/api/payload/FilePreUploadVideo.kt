package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 预上传视频接口载荷
 */
object FilePreUploadVideo {

    data class Request(
        /** 文件名 */

        @field:NotEmpty(message = "文件名不能为空")
        val fileName: String = null,
        /** 分片总数 */

        @field:NotEmpty(message = "分片总数不能为空")
        val chunks: Int = null
    )

    data class Response(
        /** 上传ID */
        var uploadId: String? = null
    )
}
