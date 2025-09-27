package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_profile.GetCustomerBasicInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCustomerBasicInfoQryHandler(
) : Query<GetCustomerBasicInfoQry.Request, GetCustomerBasicInfoQry.Response> {

    override fun exec(request: GetCustomerBasicInfoQry.Request): GetCustomerBasicInfoQry.Response {

        return GetCustomerBasicInfoQry.Response(

        )
    }
}
