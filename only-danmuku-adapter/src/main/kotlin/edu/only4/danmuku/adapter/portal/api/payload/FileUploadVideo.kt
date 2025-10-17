package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 上传视频分片接口载荷
 */
object FileUploadVideo {

    data class Request(
        /** 分片索引 */

        @field:NotEmpty(message = "分片索引不能为空")
        val chunkIndex: Int = 0,
        /** 上传ID */

        @field:NotEmpty(message = "上传ID不能为空")
        val uploadId: String = ""
    ) {
        /** 分片文件 */

        @field:NotEmpty(message = "分片文件不能为空")
        lateinit var chunkFile: org.springframework.web.multipart.MultipartFile
    }

    class Response
}
