package com.edu.only4.danmuku.adapter.application.queries;

import com.edu.only4.danmuku.application.queries.category.GetCategoryListQry
import com.only4.cap4k.ddd.core.application.query.ListQuery
import org.springframework.stereotype.Service

@Service
class GetCategoryListQryHandler(
) : ListQuery<GetCategoryListQry.Request, GetCategoryListQry.Response> {

    override fun exec(request: GetCategoryListQry.Request): List<GetCategoryListQry.Response> {

        return listOf(GetCategoryListQry.Response(

        ))

    }
}
