package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotNull

/**
 * 删除上传中的视频接口载荷
 */
object FileDelUploadVideo {

    data class Request(
        /** 上传ID */
        @field:NotNull(message = "上传ID不能为空")
        val uploadId: Long = 0L,
    )

    class Response
}
