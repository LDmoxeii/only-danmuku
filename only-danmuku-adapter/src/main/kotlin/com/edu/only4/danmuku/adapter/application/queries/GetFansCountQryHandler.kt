package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_focus.GetFansCountQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetFansCountQryHandler(
) : Query<GetFansCountQry.Request, GetFansCountQry.Response> {

    override fun exec(request: GetFansCountQry.Request): GetFansCountQry.Response {

        return GetFansCountQry.Response(

        )
    }
}
