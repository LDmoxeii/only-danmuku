package edu.only4.danmuku.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：按路径合并分片为 MP4（不接触数据库ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object MergeUploadToMp4ByPathCli {

    data class Request(
        val tempPath: String,
        val outputDir: String
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val outputDir: String,
        val mergedMp4Path: String,
        val duration: Int?,
        val fileSize: Long?,
        val failReason: String?
    )
}
