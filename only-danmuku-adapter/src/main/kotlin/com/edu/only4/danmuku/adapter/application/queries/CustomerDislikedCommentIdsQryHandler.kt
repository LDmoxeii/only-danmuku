package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_action.CustomerDislikedCommentIdsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CustomerDislikedCommentIdsQryHandler(
) : Query<CustomerDislikedCommentIdsQry.Request, CustomerDislikedCommentIdsQry.Response> {

    override fun exec(request: CustomerDislikedCommentIdsQry.Request): CustomerDislikedCommentIdsQry.Response {

        return CustomerDislikedCommentIdsQry.Response(

        )
    }
}
