package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_profile.GetCustomerStatisticQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCustomerStatisticQryHandler(
) : Query<GetCustomerStatisticQry.Request, GetCustomerStatisticQry.Response> {

    override fun exec(request: GetCustomerStatisticQry.Request): GetCustomerStatisticQry.Response {

        return GetCustomerStatisticQry.Response(

        )
    }
}
