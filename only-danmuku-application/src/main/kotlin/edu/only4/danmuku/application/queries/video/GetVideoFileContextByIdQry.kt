package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 根据成品视频文件ID获取视频上下文
 */
object GetVideoFileContextByIdQry {

    data class Request(
        val fileId: Long
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
        val videoPostId: Long,
        val fileIndex: Int
    )
}
