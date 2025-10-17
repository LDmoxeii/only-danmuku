package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载关注列表接口载荷
 */
object UHomeLoadFocusList {

    data class Request(
        /** 页码 */
        val pageNo: Int? = null
    )

    data class Response(
        /** 关注列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
