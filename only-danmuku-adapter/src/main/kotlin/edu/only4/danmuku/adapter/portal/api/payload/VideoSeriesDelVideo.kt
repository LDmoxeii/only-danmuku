package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotNull

/**
 * 删除系列中的视频请求/响应
 */
object VideoSeriesDelVideo {

    data class Request(
        /** 系列ID */
        @field:NotNull(message = "系列ID不能为空")
        val seriesId: Long,
        /** 视频ID */
        @field:NotNull(message = "视频ID不能为空")
        val videoId: Long,
    )

    class Response
}
