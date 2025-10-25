package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.category.JCategory
import edu.only4.danmuku.application.queries._share.model.category.id
import edu.only4.danmuku.application.queries._share.model.category.nodePath
import edu.only4.danmuku.application.queries._share.model.video.*
import edu.only4.danmuku.application.queries.video.CountVideosUnderCategoriesQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.like
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.stereotype.Service

/**
 * 统计分类及其子分类下的视频数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/22
 */
@Service
class CountVideosUnderCategoriesQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CountVideosUnderCategoriesQry.Request, CountVideosUnderCategoriesQry.Response> {

    override fun exec(request: CountVideosUnderCategoriesQry.Request): CountVideosUnderCategoriesQry.Response {
        val requestedIds = request.categoryIds.map { it }.toSet()
        if (requestedIds.isEmpty()) {
            return CountVideosUnderCategoriesQry.Response(totalCount = 0L)
        }

        val categoryIdsToCheck = buildSet {
            requestedIds.forEach { ancestorId ->
                val token = "/$ancestorId/"
                val ids = sqlClient.createQuery(JCategory::class) {
                    where(table.nodePath like "%$token%")
                    select(table.id)
                }.execute()
                addAll(ids)
            }
        }

        if (categoryIdsToCheck.isEmpty()) {
            return CountVideosUnderCategoriesQry.Response(totalCount = 0L)
        }

        val idsList = categoryIdsToCheck.toList()
        val total = sqlClient.createQuery(JVideo::class) {
            val categoryPredicate = table.categoryId valueIn idsList
            val parentPredicate = table.pCategoryId valueIn idsList
            where(
                or(categoryPredicate, parentPredicate)
            )
            select(count(table.id))
        }.execute().firstOrNull() ?: 0L

        return CountVideosUnderCategoriesQry.Response(totalCount = total)
    }
}
