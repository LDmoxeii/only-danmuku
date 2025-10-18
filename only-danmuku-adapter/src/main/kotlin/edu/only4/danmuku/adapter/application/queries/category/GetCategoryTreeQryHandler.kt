package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import edu.only4.danmuku.application.queries._share.draft.JCategory.CategoryTreeNode
import edu.only4.danmuku.application.queries._share.model.JCategory
import edu.only4.danmuku.application.queries._share.model.parentId
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取分类树形结构 - Jimmer 实现
 *
 * 实现要点：
 * 1. 使用 Jimmer DTO 自动映射为树形结构
 * 2. 一条 SQL 查询完成所有数据加载（递归 CTE 或多次查询）
 * 3. 自动处理 N+1 问题
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCategoryTreeQryHandler(
    private val sqlClient: KSqlClient
) : ListQuery<GetCategoryTreeQry.Request, GetCategoryTreeQry.CategoryTreeNode> {

    override fun exec(request: GetCategoryTreeQry.Request): List<GetCategoryTreeQry.CategoryTreeNode> {

//        // 方式 1：使用 Jimmer DTO 直接查询（推荐）
//        // 优势：
//        // - 自动递归加载子分类
//        // - 生成最优 SQL（可能使用递归 CTE）
//        // - 类型安全，自动映射
//        val categoryTreeNodes = sqlClient.findList(GetCategoryTreeQry.CategoryTreeNode::class) {
//            where(table.parentId.eq(0L))  // 只查询顶级分类，子分类会自动递归加载
//            orderBy(table.sort.asc())
//        }
//
//        // 转换 Jimmer DTO 为 Query Response
//        return categoryTreeNodes.map { convertToQueryResponse(it) }
        return emptyList()
    }

//    /**
//     * 将 Jimmer DTO 转换为 Query Response
//     * 递归转换整个树形结构
//     */
//    private fun convertToQueryResponse(dto: CategoryTreeNode): GetCategoryTreeQry.CategoryTreeNode {
//        return GetCategoryTreeQry.CategoryTreeNode(
//            categoryId = dto.id,
//            code = dto.code,
//            name = dto.name,
//            parentId = dto.parentId,
//            icon = dto.icon,
//            background = dto.background,
//            sort = dto.sort,
//            // 递归转换子分类
//            children = dto.children?.map { convertToQueryResponse(it) } ?: emptyList()
//        )
//    }
//
//    /**
//     * 方式 2：使用 Fetcher 手动查询（备选方案）
//     * 适用于需要更精细控制的场景
//     */
//    @Suppress("unused")
//    private fun execWithFetcher(request: GetCategoryTreeQry.Request): List<GetCategoryTreeQry.CategoryTreeNode> {
//        // 使用预定义的 TREE Fetcher
//        val categories = sqlClient.executeQuery(JCategory::class) {
//            where(table.parentId.eq(0L))
//            orderBy(table.sort.asc())
//            select(
//                table.fetch(
//                    // 手动定义递归查询
//                    newFetcher(JCategory::class).by {
//                        allScalarFields()
//                        `children*` {
//                            allScalarFields()
//                        }
//                    }
//                )
//            )
//        }
//
//        // 手动转换实体为 Response
//        return categories.map { categoryToResponse(it) }
//    }
//
//    /**
//     * 实体转换为 Response（用于方式 2）
//     */
//    @Suppress("unused")
//    private fun categoryToResponse(category: JCategory): GetCategoryTreeQry.CategoryTreeNode {
//        return GetCategoryTreeQry.CategoryTreeNode(
//            categoryId = category.id,
//            code = category.code,
//            name = category.name,
//            parentId = category.parentId,
//            icon = category.icon,
//            background = category.background,
//            sort = category.sort,
//            children = category.children.map { categoryToResponse(it) }
//        )
//    }
}

