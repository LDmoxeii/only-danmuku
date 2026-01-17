package edu.only4.danmuku.application.distributed.clients.statistics

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 上报视频播放在线人数
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
object ReportVideoPlayOnlineCli {

    data class Request(
        val fileId: Long,
        val deviceId: String
    ) : RequestParam<Response>

    data class Response(
        val current: Long
    )
}
