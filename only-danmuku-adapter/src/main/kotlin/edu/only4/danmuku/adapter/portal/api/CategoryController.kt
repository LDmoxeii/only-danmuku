package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.CategoryLoadAll
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 分类控制器 - 优化版（支持 Jimmer）
 *
 * 优化点：
 * 1. 保留原有基于 CQRS 的实现（方式 1）
 * 2. 新增直接使用 Jimmer DTO 的实现（方式 2 - 可选）
 * 3. 提供两种实现的性能对比
 */
@RestController
@RequestMapping("/category/v2")
@Validated
class CategoryController {

    @PostMapping("/loadAllCategory")
    fun categoryLoadAll(@RequestBody @Validated request: CategoryLoadAll.Request): List<CategoryLoadAll.Response> {
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
