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
        /** 视频ID */
        val videoId: Long,
    ) : ListQueryParam<Response>

    data class Response(
        /** 文件ID */
        val fileId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 分片索引 */
        val fileIndex: Int? = null,
        /** 文件名称 */
        val fileName: String? = null,
        /** 文件大小 */
        val fileSize: Long? = null,
        /** 文件路径 */
        val filePath: String? = null,
        /** 时长(秒) */
        val duration: Int? = null,
    )
}
