package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.user.ValidatePasswordQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class ValidatePasswordQryHandler(
) : Query<ValidatePasswordQry.Request, ValidatePasswordQry.Response> {

    override fun exec(request: ValidatePasswordQry.Request): ValidatePasswordQry.Response {

        return ValidatePasswordQry.Response(

        )
    }
}
