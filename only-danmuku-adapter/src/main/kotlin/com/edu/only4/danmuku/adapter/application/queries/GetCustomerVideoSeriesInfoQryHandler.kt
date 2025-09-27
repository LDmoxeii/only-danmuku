package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCustomerVideoSeriesInfoQryHandler(
) : Query<GetCustomerVideoSeriesInfoQry.Request, GetCustomerVideoSeriesInfoQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesInfoQry.Request): GetCustomerVideoSeriesInfoQry.Response {

        return GetCustomerVideoSeriesInfoQry.Response(

        )
    }
}
