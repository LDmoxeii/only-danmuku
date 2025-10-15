package edu.only4.danmuku.adapter.application.queries.category

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.category.CategoryExistsByCodeQry

import org.springframework.stereotype.Service

/**
 * 根据分类编码判断分类是否存在
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CategoryExistsByCodeQryHandler(
) : Query<CategoryExistsByCodeQry.Request, CategoryExistsByCodeQry.Response> {

    override fun exec(request: CategoryExistsByCodeQry.Request): CategoryExistsByCodeQry.Response {

        return CategoryExistsByCodeQry.Response(

        )
    }
}
