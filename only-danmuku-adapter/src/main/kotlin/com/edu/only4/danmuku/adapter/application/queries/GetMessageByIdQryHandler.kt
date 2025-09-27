package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_message.GetMessageByIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetMessageByIdQryHandler(
) : Query<GetMessageByIdQry.Request, GetMessageByIdQry.Response> {

    override fun exec(request: GetMessageByIdQry.Request): GetMessageByIdQry.Response {

        return GetMessageByIdQry.Response(

        )
    }
}
