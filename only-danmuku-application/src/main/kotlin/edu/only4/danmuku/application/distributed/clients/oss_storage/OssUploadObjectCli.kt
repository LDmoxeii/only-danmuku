package edu.only4.danmuku.application.distributed.clients.oss_storage

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 上传单对象到 OSS
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object OssUploadObjectCli {

    data class Request(
        val objectKey: String,
        val localPath: String,
        val contentType: String?,
        val metadata: String?
    ) : RequestParam<Response>

    data class Response(
        val url: String,
        val eTag: String?,
        val size: Long
    )
}
