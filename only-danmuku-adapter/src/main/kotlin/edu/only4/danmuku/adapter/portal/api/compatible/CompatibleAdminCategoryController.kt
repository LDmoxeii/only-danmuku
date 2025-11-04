package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryLoad
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategorySave
import edu.only4.danmuku.application.commands.category.DeleteCategoryCmd
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
        return treeResult.map { AdminCategoryLoad.Converter.INSTANCE.fromApp(it) }
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
                val req = AdminCategorySave.Request(
                    pCategoryId = parentId,
                    categoryId = null,
                    categoryCode = categoryCode,
                    categoryName = categoryName,
                    icon = icon,
                    background = background,
                )
                Mediator.commands.send(
                    AdminCategorySave.Converter.INSTANCE.toCreateCmd(req)
                )
            }

            else -> {
                val req = AdminCategorySave.Request(
                    pCategoryId = parentId,
                    categoryId = categoryId,
                    categoryCode = categoryCode,
                    categoryName = categoryName,
                    icon = icon,
                    background = background,
                )
                Mediator.commands.send(
                    AdminCategorySave.Converter.INSTANCE.toUpdateCmd(req)
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

}
