package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 保存视频互动设置接口载荷
 */
object UCenterSaveVideoInteraction {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = "",
        /** 互动设置 */
        val interaction: String? = null
    )

    class Response()
}
