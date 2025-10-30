package edu.only4.danmuku.application.queries.file

import com.only4.cap4k.ddd.core.application.RequestParam
import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频TS分片资源查询
 *
 * 用于读取视频的TS片段文件
 */
object GetVideoResourceTsQry {

    data class Request(
        val fileId: Long,
        @field:NotEmpty(message = "TS文件名不能为空")
        val ts: String,
    ) : RequestParam<Response>

    data class Response(
        /** 文件的完整路径 */
        val filePath: String,
        /** 文件是否存在 */
        val exists: Boolean,
    )
}
