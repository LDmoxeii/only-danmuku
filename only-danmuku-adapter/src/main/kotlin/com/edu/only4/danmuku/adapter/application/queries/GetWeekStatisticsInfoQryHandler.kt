package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetWeekStatisticsInfoQryHandler(
) : Query<GetWeekStatisticsInfoQry.Request, GetWeekStatisticsInfoQry.Response> {

    override fun exec(request: GetWeekStatisticsInfoQry.Request): GetWeekStatisticsInfoQry.Response {

        return GetWeekStatisticsInfoQry.Response(

        )
    }
}
