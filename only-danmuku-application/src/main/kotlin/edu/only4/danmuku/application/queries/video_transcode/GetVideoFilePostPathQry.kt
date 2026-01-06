package edu.only4.danmuku.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 根据稿件态 filePostId 获取播放路径（优先转码输出前缀）
 */
object GetVideoFilePostPathQry {

    data class Request(
        val filePostId: Long
    ) : RequestParam<Response>

    data class Response(
        val filePath: String?
    )
}
