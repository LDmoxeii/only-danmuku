package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.category.GetParentCategoriesQry

import org.springframework.stereotype.Service

/**
 * 获取父级分类列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetParentCategoriesQryHandler(
) : Query<GetParentCategoriesQry.Request, GetParentCategoriesQry.Response> {

    override fun exec(request: GetParentCategoriesQry.Request): GetParentCategoriesQry.Response {

        return GetParentCategoriesQry.Response(

        )
    }
}
