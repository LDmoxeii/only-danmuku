package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * 保存视频系列接口载荷
 */
object VideoSeriesSave {

    data class Request(
        /** 系列ID(更新时传) */
        val seriesId: Int? = null,
        /** 系列名称 */

        @field:NotEmpty(message = "系列名称不能为空")
        @field:Size(max = 100, message = "长度不能超过100个字符")
        val seriesName: String = "",
        /** 系列描述 */

        @field:Size(max = 200, message = "长度不能超过200个字符")
        val seriesDescription: String? = null,
        /** 视频ID列表(逗号分隔) */
        val videoIds: String? = null
    )

    class Response
}
