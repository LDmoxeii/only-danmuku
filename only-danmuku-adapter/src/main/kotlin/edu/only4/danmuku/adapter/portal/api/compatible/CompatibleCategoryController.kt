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
        // 获取树形结构
        val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
        // 转换为前端需要的格式
        return treeResult.map { qryResponseToApiResponse(it) }

    }

    private fun qryResponseToApiResponse(node: GetCategoryTreeQry.Response): CategoryLoadAll.Response {
        return CategoryLoadAll.Response(
            categoryId = node.categoryId.toString(),
            code = node.code,
            name = node.name,
            icon = node.icon,
            background = node.background,
            sort = node.sort.toInt(),
            children = node.children.map { qryResponseToApiResponse(it) }
        )
    }

}
