package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载视频系列列表接口载荷
 */
object VideoSeriesLoad {

    data class Request(
        /** 用户ID */
        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = ""
    )

    data class Response(
        /** 系列列表 */
        var list: List<SeriesItem>? = null
    )

    data class SeriesItem(
        var seriesId: String? = null,
        var seriesName: String? = null,
        var seriesDescription: String? = null,
        var sort: Int? = null,
        var videoCount: Int? = null,
        var createTime: String? = null
    )
}
