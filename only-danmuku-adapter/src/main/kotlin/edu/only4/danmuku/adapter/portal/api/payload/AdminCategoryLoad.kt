package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载分类列表(树形结构)接口载荷
 */
object AdminCategoryLoad {

    data class Request(
        /** 是否转换为树形结构 */
        val convert2Tree: Boolean? = null
    )

    data class Response(
        /** 分类列表 */
        var list: List<Any>? = null
    )
}
