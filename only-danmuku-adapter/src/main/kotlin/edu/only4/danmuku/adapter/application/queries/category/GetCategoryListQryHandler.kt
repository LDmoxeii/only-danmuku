package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.category.GetCategoryListQry

import org.springframework.stereotype.Service

/**
 * 获取分类列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCategoryListQryHandler(
) : ListQuery<GetCategoryListQry.Request, GetCategoryListQry.Response> {

    override fun exec(request: GetCategoryListQry.Request): List<GetCategoryListQry.Response> {

        return listOf(GetCategoryListQry.Response(

        ))

    }
}
