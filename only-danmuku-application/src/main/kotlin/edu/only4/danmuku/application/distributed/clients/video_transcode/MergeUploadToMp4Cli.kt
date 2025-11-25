package edu.only4.danmuku.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：合并上传分片为临时 MP4，返回相对输出目录、时长、文件大小
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object MergeUploadToMp4Cli {

    data class Request(
        val uploadId: Long,
        val customerId: Long,
        val videoId: Long,
        val fileIndex: Int,
        val tempPath: String
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean,
        val outputDir: String = "",
        val mergedMp4Path: String = "",
        val duration: Int? = null,
        val fileSize: Long? = null,
        val failReason: String? = null,
    )
}
