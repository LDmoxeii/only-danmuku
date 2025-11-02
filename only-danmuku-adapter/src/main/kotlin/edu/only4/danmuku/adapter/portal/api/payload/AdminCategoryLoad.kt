package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载分类列表(树形结构)接口载荷
 */
object AdminCategoryLoad {

    data class Item(
        var categoryId: Long,
        var categoryCode: String,
        var categoryName: String,
        var parentId: Long?,
        var icon: String?,
        var background: String?,
        var sort: Int,
        var children: List<Item>,
    )
}
