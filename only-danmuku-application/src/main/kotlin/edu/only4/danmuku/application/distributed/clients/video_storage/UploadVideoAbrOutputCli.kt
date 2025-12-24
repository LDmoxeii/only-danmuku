package edu.only4.danmuku.application.distributed.clients.video_storage

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 上传 ABR 输出目录到 OSS（含 master/variants/segments）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object UploadVideoAbrOutputCli {

    data class Request(
        val localOutputDir: String,
        val encOutputDir: String?,
        val objectPrefix: String?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val storagePrefix: String,
        val encPrefix: String?,
        val failReason: String?
    )
}
