package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.CategoryLoadAll
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 分类控制器 - 优化版（支持 Jimmer）
 *
 * 优化点：
 * 1. 保留原有基于 CQRS 的实现（方式 1）
 * 2. 新增直接使用 Jimmer DTO 的实现（方式 2 - 可选）
 * 3. 提供两种实现的性能对比
 */
@RestController
@RequestMapping("/category")
@Validated
class CategoryController(
    // 注入 Jimmer SqlClient（可选，用于方式 2）
    private val sqlClient: KSqlClient? = null
) {

//    /**
//     * 加载所有分类（树形结构）
//     * 方式 1：使用 CQRS Query Handler（推荐用于复杂业务逻辑）
//     */
//    @GetMapping("/loadAllCategory")
//    fun categoryLoadAll(): List<CategoryLoadAll.CategoryItem> {
//        // 调用查询获取分类树形结构
//        val queryResult = Mediator.queries.send(GetCategoryTreeQry.Request())
//
//        // 递归转换为前端需要的格式
//        return queryResult.categoryTree.map { convertToCategoryItem(it) }
//    }
//
//    /**
//     * 加载所有分类（树形结构）- Jimmer DTO 直接版本
//     * 方式 2：直接使用 Jimmer DTO（适用于简单查询，性能更优）
//     *
//     * 优势：
//     * - 减少一次数据转换（DTO → Response → Payload）
//     * - 更少的代码
//     * - 更好的性能
//     *
//     * 使用场景：
//     * - 简单的 CRUD 查询
//     * - 不需要复杂业务逻辑
//     * - 对性能要求高的场景
//     */
//    @GetMapping("/loadAllCategory/v2")
//    fun categoryLoadAllDirect(): List<CategoryLoadAll.CategoryItem> {
//        // 检查 SqlClient 是否注入
//        if (sqlClient == null) {
//            throw UnsupportedOperationException("Jimmer SqlClient not configured. Please add Jimmer dependency.")
//        }
//
//        // 直接使用 Jimmer DTO 查询
//        val categoryTreeNodes = sqlClient.findList(CategoryTreeNode::class) {
//            where(table.parentId.eq(0L))
//            orderBy(table.sort.asc())
//        }
//
//        // 一次转换：Jimmer DTO → Payload
//        return categoryTreeNodes.map { convertJimmerDtoToCategoryItem(it) }
//    }
//
//    /**
//     * 递归转换分类树节点为前端格式（用于方式 1）
//     */
//    private fun convertToCategoryItem(node: GetCategoryTreeQry.CategoryTreeNode): CategoryLoadAll.CategoryItem {
//        return CategoryLoadAll.CategoryItem(
//            categoryId = node.categoryId.toString(),
//            code = node.code,
//            name = node.name,
//            pCategoryId = if (node.parentId == 0L) null else node.parentId.toString(),
//            icon = node.icon,
//            background = node.background,
//            sort = node.sort.toInt(),
//            children = if (node.children.isNotEmpty()) {
//                node.children.map { convertToCategoryItem(it) }
//            } else {
//                null
//            }
//        )
//    }
//
//    /**
//     * 递归转换 Jimmer DTO 为前端格式（用于方式 2）
//     */
//    private fun convertJimmerDtoToCategoryItem(dto: CategoryTreeNode): CategoryLoadAll.CategoryItem {
//        return CategoryLoadAll.CategoryItem(
//            categoryId = dto.id.toString(),
//            code = dto.code,
//            name = dto.name,
//            pCategoryId = if (dto.parentId == 0L) null else dto.parentId.toString(),
//            icon = dto.icon,
//            background = dto.background,
//            sort = dto.sort.toInt(),
//            children = dto.children?.let { childList ->
//                if (childList.isNotEmpty()) {
//                    childList.map { convertJimmerDtoToCategoryItem(it) }
//                } else {
//                    null
//                }
//            }
//        )
//    }

}
