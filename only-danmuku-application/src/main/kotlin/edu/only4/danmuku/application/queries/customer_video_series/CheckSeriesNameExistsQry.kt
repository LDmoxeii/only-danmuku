package edu.only4.danmuku.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 检查用户下系列名称是否已存在
 */
object CheckSeriesNameExistsQry {

    data class Request(
        val customerId: Long,
        val seriesName: String,
        val excludeSeriesId: Long? = null,
    ) : RequestParam<Response>

    data class Response(
        val exists: Boolean,
    )
}

