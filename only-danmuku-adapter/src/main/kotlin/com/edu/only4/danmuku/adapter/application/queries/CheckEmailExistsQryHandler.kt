package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.user.CheckEmailExistsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CheckEmailExistsQryHandler(
) : Query<CheckEmailExistsQry.Request, CheckEmailExistsQry.Response> {

    override fun exec(request: CheckEmailExistsQry.Request): CheckEmailExistsQry.Response {

        return CheckEmailExistsQry.Response(

        )
    }
}
