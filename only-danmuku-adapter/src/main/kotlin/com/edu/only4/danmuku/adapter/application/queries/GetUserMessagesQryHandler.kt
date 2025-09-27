package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_message.GetUserMessagesQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUserMessagesQryHandler(
) : Query<GetUserMessagesQry.Request, GetUserMessagesQry.Response> {

    override fun exec(request: GetUserMessagesQry.Request): GetUserMessagesQry.Response {

        return GetUserMessagesQry.Response(

        )
    }
}
