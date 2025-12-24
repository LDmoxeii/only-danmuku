package edu.only4.danmuku.application.distributed.clients.oss_storage

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 上传目录到 OSS（用于 HLS 输出）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object OssUploadDirectoryCli {

    data class Request(
        val localDir: String,
        val objectPrefix: String,
        val includeGlob: String?
    ) : RequestParam<Response>

    data class Response(
        val uploadedCount: Int,
        val prefix: String
    )
}
