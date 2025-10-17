package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除视频系列接口载荷
 */
object VideoSeriesDel {

    data class Request(
        /** 系列ID */

        @field:NotEmpty(message = "系列ID不能为空")
        val seriesId: Int = null
    )

    class Response
}
