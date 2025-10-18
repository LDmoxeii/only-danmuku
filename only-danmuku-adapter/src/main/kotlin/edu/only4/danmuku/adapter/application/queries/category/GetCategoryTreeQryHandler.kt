package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.fetcher.CategoryFetcher
import edu.only4.danmuku.application.queries._share.model.JCategory
import edu.only4.danmuku.application.queries._share.model.parentId
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
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
) : ListQuery<GetCategoryTreeQry.Request, GetCategoryTreeQry.ResponseItem> {

    override fun exec(request: GetCategoryTreeQry.Request): List<GetCategoryTreeQry.ResponseItem> {

        // 使用 Jimmer Fetcher 查询分类树形结构
        // 优势：
        // - 使用预定义的 TREE Fetcher，包含递归子分类加载
        // - 生成最优 SQL（使用递归 CTE）
        // - 自动处理 N+1 问题
        val categories = sqlClient.executeQuery(JCategory::class) {
            where(table.parentId eq 0L)  // 只查询顶级分类
            select(table.fetch(CategoryFetcher.TREE))  // 使用 TREE Fetcher
        }

        // 转换 Jimmer 实体为 Query Response
        return categories.map { categoryToResponse(it) }
    }

    /**
     * 实体转换为 Response
     * 递归转换整个树形结构
     */
    private fun categoryToResponse(category: JCategory): GetCategoryTreeQry.ResponseItem {
        return GetCategoryTreeQry.ResponseItem(
            categoryId = category.id,
            code = category.code,
            name = category.name,
            parentId = category.parentId,
            icon = category.icon,
            background = category.background,
            sort = category.sort,
            children = category.children.map { categoryToResponse(it) }
        )
    }
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

