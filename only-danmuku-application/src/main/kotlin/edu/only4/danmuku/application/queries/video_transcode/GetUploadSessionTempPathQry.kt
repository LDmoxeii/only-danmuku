package edu.only4.danmuku.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 查询上传会话的临时目录 tempPath
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object GetUploadSessionTempPathQry {

    data class Request(
        val uploadId: Long
    ) : RequestParam<Response>

    data class Response(
        val tempPath: String
    )
}
