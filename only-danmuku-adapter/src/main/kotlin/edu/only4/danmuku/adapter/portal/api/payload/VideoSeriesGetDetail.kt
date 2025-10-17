package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取系列详情接口载荷
 */
object VideoSeriesGetDetail {

    data class Request(
        /** 系列ID */

        @field:NotEmpty(message = "系列ID不能为空")
        val seriesId: Int = 0
    )

    data class Response(
        /** 系列信息 */
        var seriesInfo: Map<String, Any>? = null,
        /** 系列视频列表 */
        var videoList: List<Any>? = null
    )
}
