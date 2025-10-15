package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.category.GetSubCategoriesQry

import org.springframework.stereotype.Service

/**
 * 获取子分类列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetSubCategoriesQryHandler(
) : Query<GetSubCategoriesQry.Request, GetSubCategoriesQry.Response> {

    override fun exec(request: GetSubCategoriesQry.Request): GetSubCategoriesQry.Response {

        return GetSubCategoriesQry.Response(

        )
    }
}
