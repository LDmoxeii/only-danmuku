package edu.only4.danmuku.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：上传 ABR 输出目录到对象存储（不接触数据库ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object UploadVideoAbrOutputCli {

    data class Request(
        val outputDir: String,
        val objectPrefix: String
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val storagePrefix: String?,
        val failReason: String?
    )
}
