package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_message.GetUnreadMessageCountQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUnreadMessageCountQryHandler(
) : Query<GetUnreadMessageCountQry.Request, GetUnreadMessageCountQry.Response> {

    override fun exec(request: GetUnreadMessageCountQry.Request): GetUnreadMessageCountQry.Response {

        return GetUnreadMessageCountQry.Response(

        )
    }
}
