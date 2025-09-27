package com.edu.only4.danmuku.adapter.application.queries;

import com.edu.only4.danmuku.application.queries.customer_message.GetMessageListQry
import com.only4.cap4k.ddd.core.application.query.ListQuery
import org.springframework.stereotype.Service

@Service
class GetMessageListQryHandler(
) : ListQuery<GetMessageListQry.Request, GetMessageListQry.Response> {

    override fun exec(request: GetMessageListQry.Request): List<GetMessageListQry.Response > {

        return listOf(GetMessageListQry.Response(

        ))

    }
}
