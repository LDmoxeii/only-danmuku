package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载消息列表接口载荷
 */
object MessageLoad {

    data class Request(
        /** 消息类型 */

        @field:NotEmpty(message = "消息类型不能为空")
        val messageType: Int = null,
        /** 页码 */
        val pageNo: Int? = null
    )

    data class Response(
        /** 消息列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
