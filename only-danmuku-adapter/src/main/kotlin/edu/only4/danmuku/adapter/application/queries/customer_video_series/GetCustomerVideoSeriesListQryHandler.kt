package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.CustomerVideoSeries
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户视频系列列表
 */
@Service
class GetCustomerVideoSeriesListQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetCustomerVideoSeriesListQry.Request, GetCustomerVideoSeriesListQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesListQry.Request): List<GetCustomerVideoSeriesListQry.Response> {
        // 查询系列及其视频封面信息
        val seriesDetailList =
            sqlClient.createQuery(CustomerVideoSeries::class) {
                where(table.customerId eq request.userId)
                select(table.fetchBy {
                    seriesName()
                    seriesDescription()
                    sort()
                    createTime()
                    seriesVideos {
                        sort()
                        video {
                            videoCover()
                        }
                    }
                })
            }.execute()

        // 按列表 sort 升序排列
        val ordered = seriesDetailList.sortedBy { it.sort }

        return ordered.map { series ->
            val firstVideoCover = series.seriesVideos
                .minByOrNull { sv -> sv.sort }?.video?.videoCover

            GetCustomerVideoSeriesListQry.Response(
                seriesId = series.id,
                seriesName = series.seriesName,
                seriesDescription = series.seriesDescription,
                sort = series.sort,
                videoCount = series.seriesVideos.size,
                cover = firstVideoCover,
                createTime = series.createTime ?: 0L,
                updateTime = series.createTime
            )
        }
    }
}
