package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.customer_profile.GetCustomersByNicknameQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCustomersByNicknameQryHandler(
) : Query<GetCustomersByNicknameQry.Request, GetCustomersByNicknameQry.Response> {

    override fun exec(request: GetCustomersByNicknameQry.Request): GetCustomersByNicknameQry.Response {

        return GetCustomersByNicknameQry.Response(

        )
    }
}
