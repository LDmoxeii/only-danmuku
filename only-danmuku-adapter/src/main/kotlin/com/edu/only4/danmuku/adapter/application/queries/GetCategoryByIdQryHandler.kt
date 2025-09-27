package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.category.GetCategoryByIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCategoryByIdQryHandler(
) : Query<GetCategoryByIdQry.Request, GetCategoryByIdQry.Response> {

    override fun exec(request: GetCategoryByIdQry.Request): GetCategoryByIdQry.Response {

        return GetCategoryByIdQry.Response(

        )
    }
}
