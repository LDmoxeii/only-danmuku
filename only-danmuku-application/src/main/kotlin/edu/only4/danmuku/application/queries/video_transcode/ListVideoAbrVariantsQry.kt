package edu.only4.danmuku.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 查询指定 fileId 的可用清晰度档位列表（quality、宽高、码率、路径）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object ListVideoAbrVariantsQry {

    data class Request(
        val videoFilePostId: Long?,
        val videoFileId: Long?
    ) : ListQueryParam<Response>

    data class Response(
        val qualities: List<String>,
        val variantJson: String
    )
}
