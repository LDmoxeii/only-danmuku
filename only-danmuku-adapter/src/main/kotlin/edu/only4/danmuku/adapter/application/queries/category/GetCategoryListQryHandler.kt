package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.draft.CategorySimple
import edu.only4.danmuku.application.queries._share.model.parentId
import edu.only4.danmuku.application.queries._share.model.sort
import edu.only4.danmuku.application.queries.category.GetCategoryListQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.springframework.stereotype.Service

/**
 * 获取分类列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCategoryListQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetCategoryListQry.Request, GetCategoryListQry.Response> {

    override fun exec(request: GetCategoryListQry.Request): List<GetCategoryListQry.Response> {

        val categoryList = sqlClient.findAll(CategorySimple::class) {
            where(table.parentId `eq?` request.parentId)
            orderBy(table.sort.asc())
        }

        return categoryList.map { dto ->
            GetCategoryListQry.Response(
                categoryId = dto.id,
                code = dto.code,
                name = dto.name,
                parentId = dto.parentId ?: 0L,
                icon = dto.icon,
                background = dto.background,
                sort = dto.sort
            )
        }
    }
}
