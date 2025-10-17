package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载所有分类接口载荷
 */
object CategoryLoadAll {

    class Request

    data class Response(
        /** 分类列表 */
        var list: List<Any>? = null
    )
}
