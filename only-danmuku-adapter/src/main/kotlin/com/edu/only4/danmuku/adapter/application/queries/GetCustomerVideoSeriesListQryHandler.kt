package com.edu.only4.danmuku.adapter.application.queries;

import com.edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import com.only4.cap4k.ddd.core.application.query.ListQuery
import org.springframework.stereotype.Service

@Service
class GetCustomerVideoSeriesListQryHandler(
) : ListQuery<GetCustomerVideoSeriesListQry.Request, GetCustomerVideoSeriesListQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesListQry.Request): List<GetCustomerVideoSeriesListQry.Response > {

        return listOf(GetCustomerVideoSeriesListQry.Response(

        ))

    }
}
