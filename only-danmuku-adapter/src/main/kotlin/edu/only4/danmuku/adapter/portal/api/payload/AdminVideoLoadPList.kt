package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载视频分片列表接口载荷
 */
object AdminVideoLoadPList {

    data class Request(
        /** 视频ID */
        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String? = null
    )

    /**
     * 视频文件分片项
     */
    data class FileItem(
        /** 文件ID */
        var fileId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 分片索引 */
        var fileIndex: Int? = null,
        /** 文件名称 */
        var fileName: String? = null,
        /** 文件大小 */
        var fileSize: Long? = null,
        /** 文件路径 */
        var filePath: String? = null,
        /** 时长(秒) */
        var duration: Int? = null
    )
}
