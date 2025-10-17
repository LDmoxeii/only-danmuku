package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 上传图片接口载荷
 */
object AdminFileUploadImage {

    data class Request(
        /** 图片文件 */

        @field:NotEmpty(message = "图片文件不能为空")
        val file: org.springframework.web.multipart.MultipartFile = null,
        /** 是否生成缩略图 */

        @field:NotEmpty(message = "是否生成缩略图不能为空")
        val createThumbnail: Boolean = null
    )

    data class Response(
        /** 文件路径 */
        var filePath: String? = null
    )
}
