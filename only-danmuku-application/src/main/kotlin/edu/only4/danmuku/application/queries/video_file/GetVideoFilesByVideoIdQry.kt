package edu.only4.danmuku.application.queries.video_file

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 根据视频ID获取文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoFilesByVideoIdQry {

    data class Request(
        /** 视频ID */
        val videoId: Long
    ) : RequestParam<List<Response>>

    data class Response(
        /** 文件ID */
        val fileId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 文件索引 */
        val fileIndex: Int,
        /** 文件名 */
        val fileName: String? = null,
        /** 文件大小 */
        val fileSize: Long? = null,
        /** 文件路径 */
        val filePath: String? = null,
        /** 视频时长(秒) */
        val duration: Int? = null
    )
}
