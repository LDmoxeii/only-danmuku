package edu.only4.danmuku.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：按路径转码生成 ABR 输出（不接触数据库ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object TranscodeVideoFileToAbrByPathCli {

    data class Request(
        val sourcePath: String,
        val outputDir: String,
        val profiles: String,
        val segmentDurationSec: Int = 6,
        val taskId: String?
    ) : RequestParam<Response>

    data class Response(
        val accepted: Boolean = true,
        val variantsJson: String,
        val failReason: String?
    )
}
