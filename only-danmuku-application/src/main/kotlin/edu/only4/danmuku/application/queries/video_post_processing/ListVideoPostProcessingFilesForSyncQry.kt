package edu.only4.danmuku.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 查询处理完成后的文件清单与产物摘要（含分辨率档位），用于回填稿件态文件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object ListVideoPostProcessingFilesForSyncQry {

    data class Request(
        val videoPostId: Long
    ) : ListQueryParam<Response>

    data class Response(
        val fileIndex: Int,
        val transcodeOutputPrefix: String?,
        val encryptOutputPrefix: String?,
        val variants: List<VariantItem> = emptyList(),
        val duration: Int?,
        val fileSize: Long?,
        val encryptMethod: String?,
        val keyVersion: Int?
    )

    data class VariantItem(
        val quality: String,
        val width: Int,
        val height: Int,
        val videoBitrateKbps: Int,
        val audioBitrateKbps: Int,
        val bandwidthBps: Int,
        val playlistPath: String,
        val segmentPrefix: String?,
        val segmentDuration: Int?
    )
}
