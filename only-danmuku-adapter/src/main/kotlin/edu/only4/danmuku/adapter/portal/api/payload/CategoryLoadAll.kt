package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载所有分类接口载荷
 */
object CategoryLoadAll {

    class Request

    data class Response(
        var categoryId: Long,
        var categoryCode: String,
        var categoryName: String,
        var parentCategoryId: Long,
        var icon: String?,
        var background: String?,
        var sort: Int,
        var children: List<Response>,
    )
}
