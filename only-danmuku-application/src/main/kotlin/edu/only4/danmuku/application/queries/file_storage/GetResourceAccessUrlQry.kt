package edu.only4.danmuku.application.queries.file_storage

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 根据资源 key 返回可访问 URL（OSS）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object GetResourceAccessUrlQry {

    data class Request(
        val resourceKey: String,
        val preferPresign: Boolean = false,
        val expireSeconds: Int = 600
    ) : RequestParam<Response>

    data class Response(
        val url: String
    )
}
