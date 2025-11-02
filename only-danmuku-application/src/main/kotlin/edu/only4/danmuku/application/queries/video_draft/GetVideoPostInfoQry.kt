package edu.only4.danmuku.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取视频稿件信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoPostInfoQry {

    data class Request(
        /** 视频ID */
        val videoPostId: Long,
        /** 用户ID */
        val userId: Long
    ) : RequestParam<Response>

    data class Response(
        /** 视频信息 */
        val videoInfo: VideoInfo,
        /** 视频文件列表 */
        val videoFileList: List<VideoFileItem>
    )

    data class VideoInfo(
        val videoId: Long,
        val videoCover: String?,
        val videoName: String?,
        val parentCategoryId: Long?,
        val categoryId: Long?,
        val postType: Int?,
        var originInfo: String?,
        val tags: String?,
        val introduction: String?,
        val interaction: String?,
        val status: Int,
    )

    data class VideoFileItem(
        val fileId: Long,
        val uploadId: String,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long,
        val filePath: String?,
        val duration: Int,
        val updateType: Int,
        val transferResult: Int,
    )
}
