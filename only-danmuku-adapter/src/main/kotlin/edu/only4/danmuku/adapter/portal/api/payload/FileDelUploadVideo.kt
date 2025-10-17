package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除上传中的视频接口载荷
 */
object FileDelUploadVideo {

    data class Request(
        /** 上传ID */

        @field:NotEmpty(message = "上传ID不能为空")
        val uploadId: String = null
    )

    class Response
}
