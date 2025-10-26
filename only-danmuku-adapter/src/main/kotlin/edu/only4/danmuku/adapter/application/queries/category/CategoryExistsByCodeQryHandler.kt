package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.Category
import edu.only4.danmuku.application.queries._share.model.code
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.category.CategoryExistsByCodeQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 根据分类编码判断分类是否存在
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CategoryExistsByCodeQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CategoryExistsByCodeQry.Request, CategoryExistsByCodeQry.Response> {

    override fun exec(request: CategoryExistsByCodeQry.Request): CategoryExistsByCodeQry.Response {
        // 使用 Jimmer exists() 进行高效的存在性查询
        val exists = sqlClient.exists(Category::class) {
            where(table.code eq request.code)
            where(table.id `ne?` request.excludeCategoryId)
        }

        return CategoryExistsByCodeQry.Response(
            exists = exists
        )
    }
}
