package edu.only4.danmuku.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：调用 FFmpeg/脚本生成多分辨率 HLS 与 master.m3u8
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object TranscodeVideoFileToAbrByPathCli {

    data class Request(
        val sourcePath: String,
        val outputDir: String,
        val profiles: String,
        val segmentDurationSec: Int = 6,
    ) : RequestParam<Response>

    data class Response(
        val accepted: Boolean = true,
        val variantsJson: String,
        val failReason: String? = null
    )
}
