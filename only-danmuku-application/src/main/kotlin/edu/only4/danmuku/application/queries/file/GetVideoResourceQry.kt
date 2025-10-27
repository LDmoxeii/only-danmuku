package edu.only4.danmuku.application.queries.file

import com.only4.cap4k.ddd.core.application.RequestParam
import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频资源查询（m3u8）
 *
 * 用于读取视频的主播放列表文件
 */
object GetVideoResourceQry {

    data class Request(
        /** 视频文件ID */
        @field:NotEmpty(message = "视频文件ID不能为空")
        val fileId: String,
    ) : RequestParam<Response>

    data class Response(
        /** 文件的完整路径 */
        val filePath: String,
        /** 文件是否存在 */
        val exists: Boolean,
        /** 视频ID（用于记录播放信息） */
        val videoId: Long?,
        /** 文件索引（用于记录播放信息） */
        val fileIndex: Int?,
    )
}
