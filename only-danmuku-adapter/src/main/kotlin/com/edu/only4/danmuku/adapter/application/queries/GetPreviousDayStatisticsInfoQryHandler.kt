package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetPreviousDayStatisticsInfoQryHandler(
) : Query<GetPreviousDayStatisticsInfoQry.Request, GetPreviousDayStatisticsInfoQry.Response> {

    override fun exec(request: GetPreviousDayStatisticsInfoQry.Request): GetPreviousDayStatisticsInfoQry.Response {

        return GetPreviousDayStatisticsInfoQry.Response(

        )
    }
}
