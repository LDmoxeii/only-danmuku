package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.category.JCategory
import edu.only4.danmuku.application.queries._share.model.category.id
import edu.only4.danmuku.application.queries.category.CategoryExistsByIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 根据分类ID判断分类是否存在
 */
@Service
class CategoryExistsByIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CategoryExistsByIdQry.Request, CategoryExistsByIdQry.Response> {

    override fun exec(request: CategoryExistsByIdQry.Request): CategoryExistsByIdQry.Response {
        val exists = sqlClient.exists(JCategory::class) {
            where(table.id eq request.categoryId)
        }
        return CategoryExistsByIdQry.Response(exists = exists)
    }
}

