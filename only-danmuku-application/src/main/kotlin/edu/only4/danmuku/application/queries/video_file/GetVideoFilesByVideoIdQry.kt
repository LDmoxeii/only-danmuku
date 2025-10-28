package edu.only4.danmuku.application.queries.video_file

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 根据视频ID获取文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoFilesByVideoIdQry {

    data class Request(
        val videoId: Long
    ) : ListQueryParam<Response>

    data class Response(
        var fileId: Long,
        var videoId: Long,
        var userId: Long,
        var fileIndex: Int,
        var fileName: String,
        var fileSize: Long,
        var filePath: String,
        var duration: Int
    )
}
