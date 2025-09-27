package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.statistics.GetUserActionStatisticsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUserActionStatisticsQryHandler(
) : Query<GetUserActionStatisticsQry.Request, GetUserActionStatisticsQry.Response> {

    override fun exec(request: GetUserActionStatisticsQry.Request): GetUserActionStatisticsQry.Response {

        return GetUserActionStatisticsQry.Response(

        )
    }
}
