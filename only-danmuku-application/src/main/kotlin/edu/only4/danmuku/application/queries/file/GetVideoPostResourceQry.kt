package edu.only4.danmuku.application.queries.file

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取视频资源查询（m3u8）
 *
 * 用于读取视频的主播放列表文件
 */
object GetVideoPostResourceQry {

    data class Request(
        val fileId: Long,
    ) : RequestParam<Response>

    data class Response(
        val filePath: String,
        val exists: Boolean,
        val videoId: Long,
        val fileIndex: Int,
    )
}
