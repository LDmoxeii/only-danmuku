package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.user.CheckLoginQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CheckLoginQryHandler(
) : Query<CheckLoginQry.Request, CheckLoginQry.Response> {

    override fun exec(request: CheckLoginQry.Request): CheckLoginQry.Response {

        return CheckLoginQry.Response(

        )
    }
}
