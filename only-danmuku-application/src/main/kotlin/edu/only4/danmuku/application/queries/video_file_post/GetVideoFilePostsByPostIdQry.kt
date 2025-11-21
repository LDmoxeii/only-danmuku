package edu.only4.danmuku.application.queries.video_file_post

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 查询稿件下的所有 VideoFilePost（含转码状态）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
object GetVideoFilePostsByPostIdQry {

    data class Request(
        val videoPostId: Long,
    ) : RequestParam<Response>

    data class FileItem(
        val videoFilePostId: Long,
        val uploadId: Long,
        val fileIndex: Int,
        val transferResult: Int,
        val duration: Int?,
    )

    data class Response(
        val files: List<FileItem>
    )
}
