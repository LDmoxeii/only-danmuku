package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频编辑信息接口载荷
 */
object UCenterGetVideoByVideoId {

    data class Request(
        /** 视频ID */
        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = ""
    )

    data class Response(
        /** 视频信息 */
        var videoInfo: VideoInfo? = null,
        /** 视频文件列表 */
        var videoFileList: List<VideoFileItem>? = null
    )

    /**
     * 视频信息
     */
    data class VideoInfo(
        /** 视频ID */
        var videoId: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 父分类ID */
        var pCategoryId: Int? = null,
        /** 分类ID */
        var categoryId: Int? = null,
        /** 上传类型(0自制/1转载) */
        var postType: Int? = null,
        /** 标签 */
        var tags: String? = null,
        /** 简介 */
        var introduction: String? = null,
        /** 互动设置 */
        var interaction: String? = null,
        /** 状态 */
        var status: Int? = null
    )

    /**
     * 视频文件项
     */
    data class VideoFileItem(
        /** 文件ID */
        var fileId: String? = null,
        /** 上传ID */
        var uploadId: String? = null,
        /** 文件索引 */
        var fileIndex: Int? = null,
        /** 文件名 */
        var fileName: String? = null,
        /** 文件大小 */
        var fileSize: Long? = null,
        /** 文件路径 */
        var filePath: String? = null,
        /** 时长(秒) */
        var duration: Int? = null
    )
}
