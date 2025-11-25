package edu.only4.danmuku.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：使用 ffprobe 获取源视频分辨率，为 ABR 档位决策提供输入
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object ProbeVideoResolutionCli {

    data class Request(
        val inputPath: String
    ) : RequestParam<Response>

    data class Response(
        val width: Int,
        val height: Int,
        val bitrateKbps: Int?
    )
}
