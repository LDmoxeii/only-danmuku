package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.CategoryLoadAll
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 分类控制器
 *
 */
@RestController
@RequestMapping("/category")
@Validated
class CompatibleCategoryController {

    @PostMapping("/loadAllCategory")
    fun categoryLoadAll(): List<CategoryLoadAll.Response> {
        val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
        return treeResult.map { qryResponseToApiResponse(it) }

    }

    private fun qryResponseToApiResponse(node: GetCategoryTreeQry.Response): CategoryLoadAll.Response {
        return CategoryLoadAll.Response(
            categoryId = node.categoryId,
            categoryCode = node.code,
            categoryName = node.name,
            parentCategoryId = node.parentId,
            icon = node.icon,
            background = node.background,
            sort = node.sort,
            children = node.children.map { qryResponseToApiResponse(it) }
        )
    }

}
