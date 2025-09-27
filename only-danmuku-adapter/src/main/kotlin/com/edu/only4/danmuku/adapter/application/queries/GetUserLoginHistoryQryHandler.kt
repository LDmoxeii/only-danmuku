package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.user.GetUserLoginHistoryQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUserLoginHistoryQryHandler(
) : Query<GetUserLoginHistoryQry.Request, GetUserLoginHistoryQry.Response> {

    override fun exec(request: GetUserLoginHistoryQry.Request): GetUserLoginHistoryQry.Response {

        return GetUserLoginHistoryQry.Response(

        )
    }
}
