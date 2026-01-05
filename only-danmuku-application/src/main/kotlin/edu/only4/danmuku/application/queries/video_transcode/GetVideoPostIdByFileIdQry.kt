package edu.only4.danmuku.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 前台 fileId 映射到稿件态 filePostId 及路径
 */
object GetVideoPostIdByFileIdQry {

    data class Request(
        val fileId: Long
    ) : RequestParam<Response>

    data class Response(
        val filePostId: Long,
        val filePath: String?,
        val videoPostId: Long,
        val fileIndex: Int
    )
}
