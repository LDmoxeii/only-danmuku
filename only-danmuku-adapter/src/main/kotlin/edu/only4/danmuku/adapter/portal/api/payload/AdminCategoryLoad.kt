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
        /** 分类ID */
        var categoryId: Long? = null,
        /** 分类编码 */
        var code: String? = null,
        /** 分类名称 */
        var name: String? = null,
        /** 父分类ID */
        var parentId: Long? = null,
        /** 图标 */
        var icon: String? = null,
        /** 背景图 */
        var background: String? = null,
        /** 排序 */
        var sort: Byte? = null,
        /** 子分类列表 (仅树形结构时有值) */
        var children: List<Response>? = null
    )
}
