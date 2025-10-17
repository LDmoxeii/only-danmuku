package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载粉丝列表接口载荷
 */
object UHomeLoadFansList {

    data class Request(
        /** 页码 */
        val pageNo: Int? = null
    )

    data class Response(
        /** 粉丝列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
