package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.category.GetSubCategoriesQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetSubCategoriesQryHandler(
) : Query<GetSubCategoriesQry.Request, GetSubCategoriesQry.Response> {

    override fun exec(request: GetSubCategoriesQry.Request): GetSubCategoriesQry.Response {

        return GetSubCategoriesQry.Response(

        )
    }
}
