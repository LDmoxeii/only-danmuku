package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.CustomerVideoSeries
import edu.only4.danmuku.application.queries._share.model.customer
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.seriesName
import edu.only4.danmuku.application.queries.customer_video_series.CheckSeriesNameExistsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.springframework.stereotype.Service

@Service
class CheckSeriesNameExistsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckSeriesNameExistsQry.Request, CheckSeriesNameExistsQry.Response> {

    override fun exec(request: CheckSeriesNameExistsQry.Request): CheckSeriesNameExistsQry.Response {
        val count = sqlClient.createQuery(CustomerVideoSeries::class) {
            where(table.customer.id eq request.customerId)
            where(table.seriesName eq request.seriesName)
            // 排除自身（编辑场景）
            where(table.id `ne?` request.excludeSeriesId)
            select(count(table.id))
        }.fetchOne()

        return CheckSeriesNameExistsQry.Response(count > 0)
    }
}

