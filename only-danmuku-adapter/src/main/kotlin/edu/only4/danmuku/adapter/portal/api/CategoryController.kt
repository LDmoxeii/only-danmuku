package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.CategoryLoadAll
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 分类控制器
 */
@RestController
@RequestMapping("/category")
@Validated
class CategoryController {

    /**
     * 加载所有分类（树形结构）
     */
    @GetMapping("/loadAllCategory")
    fun categoryLoadAll(): List<CategoryLoadAll.CategoryItem> {
        // 调用查询获取分类树形结构
        val queryResult = Mediator.queries.send(GetCategoryTreeQry.Request())

        // 递归转换为前端需要的格式
        return queryResult.categoryTree.map { convertToCategoryItem(it) }
    }

    /**
     * 递归转换分类树节点为前端格式
     */
    private fun convertToCategoryItem(node: GetCategoryTreeQry.CategoryTreeNode): CategoryLoadAll.CategoryItem {
        return CategoryLoadAll.CategoryItem(
            categoryId = node.categoryId.toString(),
            code = node.code,
            name = node.name,
            pCategoryId = if (node.parentId == 0L) null else node.parentId.toString(),
            icon = node.icon,
            background = node.background,
            sort = node.sort.toInt(),
            children = if (node.children.isNotEmpty()) {
                node.children.map { convertToCategoryItem(it) }
            } else {
                null
            }
        )
    }

}
