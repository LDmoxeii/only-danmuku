package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.CustomerVideoSeries.CustomerVideoSeriesDetail
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户视频系列
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomerVideoSeriesListQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetCustomerVideoSeriesListQry.Request, GetCustomerVideoSeriesListQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesListQry.Request): List<GetCustomerVideoSeriesListQry.Response> {
        // 查询用户的所有视频系列，直接使用Detail DTO来获取seriesVideos
        val seriesDetailList =
            sqlClient.findAll(CustomerVideoSeriesDetail::class) {
                where(table.customerId eq request.userId)
            }

        // 转换为响应格式
        return seriesDetailList.map { series ->
            GetCustomerVideoSeriesListQry.Response(
                seriesId = series.id,
                seriesName = series.seriesName,
                seriesDescription = series.seriesDescription,
                sort = series.sort,
                videoCount = series.seriesVideos.size,
                createTime = series.createTime ?: 0L
            )
        }
    }
}
