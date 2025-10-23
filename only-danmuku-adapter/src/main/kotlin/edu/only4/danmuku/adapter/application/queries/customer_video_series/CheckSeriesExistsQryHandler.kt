package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customer_video_series.JCustomerVideoSeries
import edu.only4.danmuku.application.queries._share.model.customer_video_series.customerId
import edu.only4.danmuku.application.queries._share.model.customer_video_series.id
import edu.only4.danmuku.application.queries.customer_video_series.CheckSeriesExistsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 检查视频系列是否存在且属于指定用户
 */
@Service
class CheckSeriesExistsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckSeriesExistsQry.Request, CheckSeriesExistsQry.Response> {

    override fun exec(request: CheckSeriesExistsQry.Request): CheckSeriesExistsQry.Response {
        val exists = sqlClient.exists(JCustomerVideoSeries::class) {
            where(table.id eq request.seriesId)
            where(table.customerId eq request.userId)
        }

        return CheckSeriesExistsQry.Response(exists = exists)
    }
}
