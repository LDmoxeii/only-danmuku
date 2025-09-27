package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCategoryTreeQryHandler(
) : Query<GetCategoryTreeQry.Request, GetCategoryTreeQry.Response> {

    override fun exec(request: GetCategoryTreeQry.Request): GetCategoryTreeQry.Response {

        return GetCategoryTreeQry.Response(

        )
    }
}
