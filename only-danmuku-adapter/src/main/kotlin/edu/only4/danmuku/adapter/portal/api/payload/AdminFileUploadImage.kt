package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import org.springframework.web.multipart.MultipartFile

/**
 * 上传图片接口载荷
 */
object AdminFileUploadImage {

    data class Request(

        /** 是否生成缩略图 */

        @field:NotEmpty(message = "是否生成缩略图不能为空")
        val createThumbnail: Boolean = false
    ) {
        /** 图片文件 */

        @field:NotEmpty(message = "图片文件不能为空")
        lateinit var file: MultipartFile
    }

    data class Response(
        /** 文件路径 */
        var filePath: String? = null
    )
}
