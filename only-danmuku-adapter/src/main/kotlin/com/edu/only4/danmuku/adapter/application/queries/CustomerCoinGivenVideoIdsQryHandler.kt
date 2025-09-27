package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_action.CustomerCoinGivenVideoIdsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CustomerCoinGivenVideoIdsQryHandler(
) : Query<CustomerCoinGivenVideoIdsQry.Request, CustomerCoinGivenVideoIdsQry.Response> {

    override fun exec(request: CustomerCoinGivenVideoIdsQry.Request): CustomerCoinGivenVideoIdsQry.Response {

        return CustomerCoinGivenVideoIdsQry.Response(

        )
    }
}
