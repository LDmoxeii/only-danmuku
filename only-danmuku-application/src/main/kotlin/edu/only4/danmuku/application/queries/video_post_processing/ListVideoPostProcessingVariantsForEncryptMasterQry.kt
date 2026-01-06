package edu.only4.danmuku.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 查询某分P的清晰度档位元数据，用于生成加密 master
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object ListVideoPostProcessingVariantsForEncryptMasterQry {

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int
    ) : ListQueryParam<Response>

    data class Response(
        val quality: String,
        val width: Int,
        val height: Int,
        val bandwidthBps: Int,
        val playlistPath: String
    )
}
