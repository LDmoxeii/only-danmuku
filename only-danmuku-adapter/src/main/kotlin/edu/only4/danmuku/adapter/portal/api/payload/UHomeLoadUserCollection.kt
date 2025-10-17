package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载用户收藏列表接口载荷
 */
object UHomeLoadUserCollection {

    data class Request(
        /** 用户ID */

        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = "",
        /** 页码 */
        val pageNo: Int? = null
    )

    data class Response(
        /** 收藏视频列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
