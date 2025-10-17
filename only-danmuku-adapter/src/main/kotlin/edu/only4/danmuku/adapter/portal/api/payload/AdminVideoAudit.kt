package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 审核视频接口载荷
 */
object AdminVideoAudit {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = "",
        /** 审核状态 */

        @field:NotEmpty(message = "审核状态不能为空")
        val status: Int = 0,
        /** 审核原因 */
        val reason: String? = null
    )

    class Response
}
