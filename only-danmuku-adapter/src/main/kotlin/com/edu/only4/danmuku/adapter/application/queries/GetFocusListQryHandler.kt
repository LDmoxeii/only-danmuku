package com.edu.only4.danmuku.adapter.application.queries;

import com.edu.only4.danmuku.application.queries.customer_focus.GetFocusListQry
import com.only4.cap4k.ddd.core.application.query.ListQuery
import org.springframework.stereotype.Service

@Service
class GetFocusListQryHandler(
) : ListQuery<GetFocusListQry.Request, GetFocusListQry.Response> {

    override fun exec(request: GetFocusListQry.Request): List<GetFocusListQry.Response> {

        return listOf(GetFocusListQry.Response(

        ))

    }
}
