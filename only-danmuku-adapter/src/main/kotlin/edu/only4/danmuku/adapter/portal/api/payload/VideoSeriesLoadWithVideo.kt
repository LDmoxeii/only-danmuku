package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载系列及关联视频接口载荷
 */
object VideoSeriesLoadWithVideo {

    data class Request(
        /** 用户ID */

        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = ""
    )

    data class Response(
        /** 系列列表(包含视频) */
        var list: List<Any>? = null
    )
}
