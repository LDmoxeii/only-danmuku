package edu.only4.danmuku.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 查询稿件最新 keyVersion（按批次）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object GetLatestVideoHlsKeyVersionQry {

    data class Request(
        val videoFilePostId: Long,
        val videoFileId: Long?
    ) : RequestParam<Response>

    data class Response(
        val keyVersion: Int?,
        val qualities: List<String>?
    )
}
