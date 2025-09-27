package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesVideoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCustomerVideoSeriesVideoQryHandler(
) : Query<GetCustomerVideoSeriesVideoQry.Request, GetCustomerVideoSeriesVideoQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesVideoQry.Request): GetCustomerVideoSeriesVideoQry.Response {

        return GetCustomerVideoSeriesVideoQry.Response(

        )
    }
}
