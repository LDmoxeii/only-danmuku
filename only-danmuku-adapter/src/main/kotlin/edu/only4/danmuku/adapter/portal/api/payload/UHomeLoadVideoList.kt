package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载用户视频列表接口载荷
 */
object UHomeLoadVideoList {

    data class Request(
        /** 用户ID */

        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = null,
        /** 类型 */
        val type: Int? = null,
        /** 页码 */
        val pageNo: Int? = null,
        /** 视频名称 */
        val videoName: String? = null,
        /** 排序类型 */
        val orderType: Int? = null
    )

    data class Response(
        /** 视频列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
