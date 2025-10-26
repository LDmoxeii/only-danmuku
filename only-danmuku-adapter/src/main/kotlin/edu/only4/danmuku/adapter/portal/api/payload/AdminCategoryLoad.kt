package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载分类列表(树形结构)接口载荷
 */
object AdminCategoryLoad {

    data class Response(
        /** 分类ID */
        var categoryId: Long,
        /** 分类编码 */
        var categoryCode: String,
        /** 分类名称 */
        var categoryName: String,
        /** 父分类ID */
        var parentId: Long?,
        /** 图标 */
        var icon: String?,
        /** 背景图 */
        var background: String?,
        /** 排序 */
        var sort: Int,
        /** 子分类列表 (仅树形结构时有值) */
        var children: List<Response>,
    )
}
