package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载视频分片列表接口载荷
 */
object AdminVideoLoadPList {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = ""
    )

    data class Response(
        /** 视频文件分片列表 */
        var list: List<Any>? = null
    )
}
