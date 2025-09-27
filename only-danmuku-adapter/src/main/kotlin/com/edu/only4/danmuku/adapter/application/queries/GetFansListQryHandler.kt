package com.edu.only4.danmuku.adapter.application.queries;

import com.edu.only4.danmuku.application.queries.customer_focus.GetFansListQry
import com.only4.cap4k.ddd.core.application.query.ListQuery
import org.springframework.stereotype.Service

@Service
class GetFansListQryHandler(
) : ListQuery<GetFansListQry.Request, GetFansListQry.Response> {

    override fun exec(request: GetFansListQry.Request): List<GetFansListQry.Response> {

        return listOf(GetFansListQry.Response(

        ))

    }
}
