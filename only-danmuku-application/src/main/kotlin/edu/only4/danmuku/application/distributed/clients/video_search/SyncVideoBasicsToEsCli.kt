package edu.only4.danmuku.application.distributed.clients.video_search

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 同步视频基础信息到 ES（upsert 文档）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/26
 */
object SyncVideoBasicsToEsCli {

    data class Request(
        val videoId: Long,
        val videoPostId: Long?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val failReason: String?
    )
}
