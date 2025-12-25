package edu.only4.danmuku.application.distributed.clients.file_upload_session

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 删除上传会话的临时目录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object DeleteUploadSessionTempDirCli {

    data class Request(
        val tempPath: String
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val failReason: String? = null
    )
}
