package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载弹幕列表接口载荷
 */
object DanmuLoad {

    data class Request(
        /** 文件ID */

        @field:NotEmpty(message = "文件ID不能为空")
        val fileId: String = null,
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = null
    )

    data class Response(
        /** 弹幕列表 */
        var list: List<Any>? = null
    )
}
