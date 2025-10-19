package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.draft.category.CategoryTreeNode
import edu.only4.danmuku.application.queries._share.model.category.parentId
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取分类树形结构
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCategoryTreeQryHandler(
    private val sqlClient: KSqlClient
) : ListQuery<GetCategoryTreeQry.Request, GetCategoryTreeQry.Response> {

    override fun exec(request: GetCategoryTreeQry.Request): List<GetCategoryTreeQry.Response> {

        val readModel = sqlClient.findAll(CategoryTreeNode::class) {
            where(table.parentId eq 0L)
        }

        return readModel.map { readModelToResponse(it) }
    }

    private fun readModelToResponse(dto: CategoryTreeNode): GetCategoryTreeQry.Response {
        return GetCategoryTreeQry.Response(
            categoryId = dto.id,
            code = dto.code,
            name = dto.name,
            parentId = dto.parentId,
            icon = dto.icon,
            background = dto.background,
            sort = dto.sort,
            // 递归转换子分类
            children = dto.children?.map { readModelToResponse(it) } ?: emptyList()
        )
    }
}

