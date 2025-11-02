package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryLoad
import edu.only4.danmuku.application.commands.category.CreateCategoryCmd
import edu.only4.danmuku.application.commands.category.DeleteCategoryCmd
import edu.only4.danmuku.application.commands.category.UpdateCategoryInfoCmd
import edu.only4.danmuku.application.commands.category.UpdateCategorySortOrderCmd
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员分类管理控制器
 */
@RestController
@RequestMapping("/admin/category")
@Validated
class CompatibleAdminCategoryController {

    @PostMapping("/loadCategory")
    fun getCategoryTree(): List<AdminCategoryLoad.Item> {
        val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
        return treeResult.map { coverToResponse(it) }
    }


    @PostMapping("/saveCategory")
    fun addCategory(
        parentId: Long,
        categoryId: Long?,
        @NotEmpty categoryCode: String,
        @NotEmpty categoryName: String,
        icon: String?,
        background: String?,
    ) {
        when (categoryId) {
            null -> {
                Mediator.commands.send(
                    CreateCategoryCmd.Request(
                        parentId = parentId,
                        code = categoryCode,
                        name = categoryName,
                        icon = icon,
                        background = background
                    )
                )
            }

            else -> {
                Mediator.commands.send(
                    UpdateCategoryInfoCmd.Request(
                        categoryId = categoryId,
                        parentId = parentId,
                        code = categoryCode,
                        name = categoryName,
                        icon = icon,
                        background = background
                    )
                )
            }
        }
    }

    @PostMapping("/delCategory")
    fun deleteCategory(categoryId: Long) {
        Mediator.commands.send(
            DeleteCategoryCmd.Request(
                categoryId = categoryId
            )
        )
    }

    @PostMapping("/changeSort")
    fun changeCategorySort(
        parentId: Long,
        categoryIds: String,
    ) {
        val categoryIdList = categoryIds.split(",")
            .map { it.trim().toLong() }

        Mediator.commands.send(
            UpdateCategorySortOrderCmd.Request(
                parentId = parentId,
                categoryIds = categoryIdList
            )
        )
    }

    private fun coverToResponse(node: GetCategoryTreeQry.Response): AdminCategoryLoad.Item {
        return AdminCategoryLoad.Item(
            categoryId = node.categoryId,
            categoryCode = node.code,
            categoryName = node.name,
            parentId = node.parentId,
            icon = node.icon,
            background = node.background,
            sort = node.sort,
            children = node.children.map { coverToResponse(it) }
        )
    }

}
