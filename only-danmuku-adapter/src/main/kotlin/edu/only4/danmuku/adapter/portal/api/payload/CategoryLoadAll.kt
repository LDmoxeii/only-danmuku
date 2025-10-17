package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载所有分类接口载荷
 */
object CategoryLoadAll {

    class Request

    /**
     * 分类项（支持树形结构）
     */
    data class CategoryItem(
        /** 分类ID */
        var categoryId: String? = null,
        /** 分类编码 */
        var code: String? = null,
        /** 分类名称 */
        var name: String? = null,
        /** 父分类ID */
        var pCategoryId: String? = null,
        /** 图标 */
        var icon: String? = null,
        /** 背景图 */
        var background: String? = null,
        /** 排序号 */
        var sort: Int? = null,
        /** 子分类列表 */
        var children: List<CategoryItem>? = null
    )
}
