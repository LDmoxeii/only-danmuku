package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 调整系列排序接口载荷
 */
object VideoSeriesChangeSort {

    data class Request(
        /** 系列ID列表(逗号分隔) */

        @field:NotEmpty(message = "系列ID列表(逗号分隔)不能为空")
        val seriesIds: String = ""
    )

    class Response
}
