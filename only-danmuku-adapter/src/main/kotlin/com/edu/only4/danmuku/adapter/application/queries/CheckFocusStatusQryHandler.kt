package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_focus.CheckFocusStatusQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CheckFocusStatusQryHandler(
) : Query<CheckFocusStatusQry.Request, CheckFocusStatusQry.Response> {

    override fun exec(request: CheckFocusStatusQry.Request): CheckFocusStatusQry.Response {

        return CheckFocusStatusQry.Response(

        )
    }
}
