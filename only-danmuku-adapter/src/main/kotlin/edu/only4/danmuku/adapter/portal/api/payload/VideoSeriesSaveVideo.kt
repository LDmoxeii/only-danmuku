package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 保存系列视频接口载荷
 */
object VideoSeriesSaveVideo {

    data class Request(
        /** 系列ID */

        @field:NotEmpty(message = "系列ID不能为空")
        val seriesId: Int = null,
        /** 视频ID列表(逗号分隔) */

        @field:NotEmpty(message = "视频ID列表(逗号分隔)不能为空")
        val videoIds: String = null
    )

    class Response
}
