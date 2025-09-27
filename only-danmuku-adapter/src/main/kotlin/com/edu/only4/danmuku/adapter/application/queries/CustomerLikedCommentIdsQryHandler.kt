package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_action.CustomerLikedCommentIdsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CustomerLikedCommentIdsQryHandler(
) : Query<CustomerLikedCommentIdsQry.Request, CustomerLikedCommentIdsQry.Response> {

    override fun exec(request: CustomerLikedCommentIdsQry.Request): CustomerLikedCommentIdsQry.Response {

        return CustomerLikedCommentIdsQry.Response(

        )
    }
}
