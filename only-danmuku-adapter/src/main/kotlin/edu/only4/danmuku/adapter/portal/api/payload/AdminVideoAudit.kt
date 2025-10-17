package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

/**
 * 审核视频接口载荷
 */
object AdminVideoAudit {

    data class Request(
        /** 视频ID */
        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String? = null,

        /** 审核状态: 0-审核中 1-审核通过 2-审核不通过 */
        @field:NotNull(message = "审核状态不能为空")
        val status: Int? = null,

        /** 审核原因 */
        val reason: String? = null
    )

    class Response
}
