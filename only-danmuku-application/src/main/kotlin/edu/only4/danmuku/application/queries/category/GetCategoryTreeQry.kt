package edu.only4.danmuku.application.queries.category

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取分类树形结构
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetCategoryTreeQry {

    class Request : RequestParam<Response>

    data class Response(
        /** 分类树形结构列表，只包含顶级分类，子分类嵌套在children中 */
        val categoryTree: List<CategoryTreeNode> = emptyList()
    )

    /**
     * 分类树节点
     */
    data class CategoryTreeNode(
        /** 分类ID */
        val categoryId: Long,
        /** 分类编码 */
        val code: String,
        /** 分类名称 */
        val name: String,
        /** 父分类ID */
        val parentId: Long,
        /** 图标 */
        val icon: String? = null,
        /** 背景图 */
        val background: String? = null,
        /** 排序号 */
        val sort: Byte,
        /** 子分类列表 */
        val children: List<CategoryTreeNode> = emptyList()
    )
}
