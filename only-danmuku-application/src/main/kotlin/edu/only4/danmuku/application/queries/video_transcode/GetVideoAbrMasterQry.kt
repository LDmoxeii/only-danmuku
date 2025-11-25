package edu.only4.danmuku.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 查询指定 fileId 的 ABR 状态（master.m3u8 路径可由 file_path 衍生）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object GetVideoAbrMasterQry {

    data class Request(
        val fileId: Long,
    ) : RequestParam<Response>

    data class Response(
        val status: String,
        val masterPath: String?
    )
}
