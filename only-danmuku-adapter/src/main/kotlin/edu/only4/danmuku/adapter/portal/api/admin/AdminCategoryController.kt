package edu.only4.danmuku.adapter.portal.api.admin

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.admin_category.GetCategoryTree
import edu.only4.danmuku.adapter.portal.api.payload.admin_category.SaveCategory
import edu.only4.danmuku.adapter.portal.api.payload.admin_category.ChangeCategorySort
import edu.only4.danmuku.application.commands.category.DeleteCategoryCmd
import edu.only4.danmuku.application.commands.category.UpdateCategorySortOrderCmd
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员分类管理控制器
 */
@RestController
@RequestMapping("/admin/category")
class AdminCategoryController {

    @PostMapping("/getCategoryTree")
    fun getCategoryTree(): List<GetCategoryTree.Item> {
        val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
        return treeResult.map { GetCategoryTree.Converter.INSTANCE.fromApp(it) }
    }

    @PostMapping("/save")
    fun save(@RequestBody @Validated request: SaveCategory.Request) {
        Mediator.commands.send(
            when (request.categoryId) {
                null -> SaveCategory.Converter.INSTANCE.toCreateCmd(request)
                else -> SaveCategory.Converter.INSTANCE.toUpdateCmd(request)
            }
        )
    }

    @PostMapping("/delete")
    fun delete(@RequestBody @Validated request: DeleteCategoryCmd.Request) {
        Mediator.commands.send(
            DeleteCategoryCmd.Request(
                categoryId = request.categoryId
            )
        )
    }

    @PostMapping("/changeSort")
    fun changeSort(@RequestBody @Validated request: ChangeCategorySort.Request) {
        val categoryIdList = request.categoryIds.split(",")
            .map { it.trim().toLong() }

        Mediator.commands.send(
            UpdateCategorySortOrderCmd.Request(
                parentId = request.parentId,
                categoryIds = categoryIdList
            )
        )
    }

}
