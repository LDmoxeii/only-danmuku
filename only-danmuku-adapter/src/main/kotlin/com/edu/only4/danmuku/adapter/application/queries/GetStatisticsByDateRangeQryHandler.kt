package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.statistics.GetStatisticsByDateRangeQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetStatisticsByDateRangeQryHandler(
) : Query<GetStatisticsByDateRangeQry.Request, GetStatisticsByDateRangeQry.Response> {

    override fun exec(request: GetStatisticsByDateRangeQry.Request): GetStatisticsByDateRangeQry.Response {

        return GetStatisticsByDateRangeQry.Response(

        )
    }
}
