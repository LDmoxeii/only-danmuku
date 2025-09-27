package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.category.CategoryExistsByCodeQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class CategoryExistsByCodeQryHandler(
) : Query<CategoryExistsByCodeQry.Request, CategoryExistsByCodeQry.Response> {

    override fun exec(request: CategoryExistsByCodeQry.Request): CategoryExistsByCodeQry.Response {

        return CategoryExistsByCodeQry.Response(

        )
    }
}
