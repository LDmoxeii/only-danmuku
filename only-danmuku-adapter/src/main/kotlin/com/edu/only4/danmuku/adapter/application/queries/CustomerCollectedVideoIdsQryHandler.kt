package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_action.CustomerCollectedVideoIdsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CustomerCollectedVideoIdsQryHandler(
) : Query<CustomerCollectedVideoIdsQry.Request, CustomerCollectedVideoIdsQry.Response> {

    override fun exec(request: CustomerCollectedVideoIdsQry.Request): CustomerCollectedVideoIdsQry.Response {

        return CustomerCollectedVideoIdsQry.Response(

        )
    }
}
