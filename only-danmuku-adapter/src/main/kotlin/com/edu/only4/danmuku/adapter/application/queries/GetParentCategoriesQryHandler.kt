package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.category.GetParentCategoriesQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetParentCategoriesQryHandler(
) : Query<GetParentCategoriesQry.Request, GetParentCategoriesQry.Response> {

    override fun exec(request: GetParentCategoriesQry.Request): GetParentCategoriesQry.Response {

        return GetParentCategoriesQry.Response(

        )
    }
}
