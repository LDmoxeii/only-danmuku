package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.statistics.GetMonthStatisticsInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetMonthStatisticsInfoQryHandler(
) : Query<GetMonthStatisticsInfoQry.Request, GetMonthStatisticsInfoQry.Response> {

    override fun exec(request: GetMonthStatisticsInfoQry.Request): GetMonthStatisticsInfoQry.Response {

        return GetMonthStatisticsInfoQry.Response(

        )
    }
}
