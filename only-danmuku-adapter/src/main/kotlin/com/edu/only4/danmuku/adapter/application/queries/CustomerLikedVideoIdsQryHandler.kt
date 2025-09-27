package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_action.CustomerLikedVideoIdsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CustomerLikedVideoIdsQryHandler(
) : Query<CustomerLikedVideoIdsQry.Request, CustomerLikedVideoIdsQry.Response> {

    override fun exec(request: CustomerLikedVideoIdsQry.Request): CustomerLikedVideoIdsQry.Response {

        return CustomerLikedVideoIdsQry.Response(

        )
    }
}
