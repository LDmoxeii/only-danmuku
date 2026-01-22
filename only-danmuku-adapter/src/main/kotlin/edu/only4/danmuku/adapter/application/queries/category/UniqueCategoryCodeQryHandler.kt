package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.Category
import edu.only4.danmuku.application.queries._share.model.code
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.category.UniqueCategoryCodeQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Service
class UniqueCategoryCodeQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueCategoryCodeQry.Request, UniqueCategoryCodeQry.Response> {

    override fun exec(request: UniqueCategoryCodeQry.Request): UniqueCategoryCodeQry.Response {
        val exists = sqlClient.exists(Category::class) {
            where(table.code eq request.code)
            where(table.id `ne?` request.excludeCategoryId)
        }

        return UniqueCategoryCodeQry.Response(
            exists = exists
        )
    }
}
