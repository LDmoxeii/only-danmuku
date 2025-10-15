package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.category.GetCategoryByIdQry

import org.springframework.stereotype.Service

/**
 * 根据ID获取分类信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCategoryByIdQryHandler(
) : Query<GetCategoryByIdQry.Request, GetCategoryByIdQry.Response> {

    override fun exec(request: GetCategoryByIdQry.Request): GetCategoryByIdQry.Response {

        return GetCategoryByIdQry.Response(

        )
    }
}
