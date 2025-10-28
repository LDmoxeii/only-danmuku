package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取视频播放文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoPlayFilesQry {

    data class Request(
        val videoId: Long,
    ) : ListQueryParam<Response>

    data class Response(
        val fileId: Long,
        val videoId: Long,
        val fileIndex: Int? = null,
        val fileName: String? = null,
        val fileSize: Long? = null,
        val filePath: String? = null,
        val duration: Int? = null,
    )
}
