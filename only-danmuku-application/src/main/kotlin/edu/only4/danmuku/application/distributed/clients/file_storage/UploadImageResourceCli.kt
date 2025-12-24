package edu.only4.danmuku.application.distributed.clients.file_storage

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 上传图片到 OSS 并返回资源 key
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object UploadImageResourceCli {

    data class Request(
        val file: MultipartFile,
        val createThumbnail: Boolean = false,
        val bizType: String
    ) : RequestParam<Response>

    data class Response(
        val resourceKey: String,
        val thumbnailKey: String?,
        val publicUrl: String?
    )
}
