package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除系列中的视频接口载荷
 */
object VideoSeriesDelVideo {

    data class Request(
        /** 系列ID */

        @field:NotEmpty(message = "系列ID不能为空")
        val seriesId: Int = 0,
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = ""
    )

    class Response
}
