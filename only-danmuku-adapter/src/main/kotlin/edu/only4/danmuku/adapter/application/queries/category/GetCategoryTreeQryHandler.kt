package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry

import org.springframework.stereotype.Service

/**
 * 获取分类树形结构
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCategoryTreeQryHandler(
) : Query<GetCategoryTreeQry.Request, GetCategoryTreeQry.Response> {

    override fun exec(request: GetCategoryTreeQry.Request): GetCategoryTreeQry.Response {

        return GetCategoryTreeQry.Response(

        )
    }
}
