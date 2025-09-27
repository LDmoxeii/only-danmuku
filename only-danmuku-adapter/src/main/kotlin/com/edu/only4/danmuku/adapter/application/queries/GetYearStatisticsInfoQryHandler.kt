package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.statistics.GetYearStatisticsInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetYearStatisticsInfoQryHandler(
) : Query<GetYearStatisticsInfoQry.Request, GetYearStatisticsInfoQry.Response> {

    override fun exec(request: GetYearStatisticsInfoQry.Request): GetYearStatisticsInfoQry.Response {

        return GetYearStatisticsInfoQry.Response(

        )
    }
}
