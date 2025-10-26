package edu.only4.danmuku.application.queries.file

import com.only4.cap4k.ddd.core.application.RequestParam
import edu.only4.danmuku.application.validater.SafeFilePath
import jakarta.validation.constraints.NotEmpty

/**
 * 获取文件资源查询
 *
 * 用于读取服务器上的静态资源文件（图片、视频等）
 */
object GetFileResourceQry {

    data class Request(
        /** 资源文件相对路径 */
        @field:NotEmpty(message = "资源文件路径不能为空")
        @field:SafeFilePath
        val sourceName: String,
    ) : RequestParam<Response>

    data class Response(
        /** 文件的完整路径 */
        val filePath: String,
        /** 文件后缀（如 .jpg, .png） */
        val fileSuffix: String,
        /** 文件是否存在 */
        val exists: Boolean,
    )
}
