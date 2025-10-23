package edu.only4.danmuku.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 检查视频系列是否存在且属于指定用户
 */
object CheckSeriesExistsQry {

    data class Request(
        /** 系列ID */
        val seriesId: Long,
        /** 用户ID */
        val userId: Long
    ) : RequestParam<Response>

    data class Response(
        /** 系列是否存在且属于该用户 */
        val exists: Boolean
    )
}
