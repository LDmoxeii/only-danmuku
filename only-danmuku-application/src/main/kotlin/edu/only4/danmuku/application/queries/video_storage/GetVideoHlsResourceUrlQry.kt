package edu.only4.danmuku.application.queries.video_storage

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取 HLS 资源 URL（master/playlist/segment）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object GetVideoHlsResourceUrlQry {

    data class Request(
        val videoFileId: Long,
        val relativePath: String
    ) : RequestParam<Response>

    data class Response(
        val url: String,
        val contentType: String?
    )
}
