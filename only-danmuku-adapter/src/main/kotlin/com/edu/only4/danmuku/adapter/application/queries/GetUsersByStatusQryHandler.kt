package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.user.GetUsersByStatusQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUsersByStatusQryHandler(
) : Query<GetUsersByStatusQry.Request, GetUsersByStatusQry.Response> {

    override fun exec(request: GetUsersByStatusQry.Request): GetUsersByStatusQry.Response {

        return GetUsersByStatusQry.Response(

        )
    }
}
