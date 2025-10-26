package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryChangeSort
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryDel
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryLoad
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategorySave
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

    @RequestMapping("/loadCategory")
    fun adminCategoryLoad(): List<AdminCategoryLoad.Response> {
        val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
        return treeResult.map { qryResponseToApiResponse(it) }
    }


    @RequestMapping("/saveCategory")
    fun adminCategorySave(
        parentId: Long,
        categoryId: Long?,
        @NotEmpty categoryCode: String,
        @NotEmpty categoryName: String,
        icon: String?,
        background: String?,
    ): AdminCategorySave.Response {
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
        return AdminCategorySave.Response()
    }

    @PostMapping("/delCategory")
    fun adminCategoryDel(categoryId: Long): AdminCategoryDel.Response {
        Mediator.commands.send(
            DeleteCategoryCmd.Request(
                categoryId = categoryId
            )
        )
        return AdminCategoryDel.Response()
    }

    @PostMapping("/changeSort")
    fun adminCategoryChangeSort(
        parentId: Long,
        categoryIds: String,
    ): AdminCategoryChangeSort.Response {
        val categoryIdList = categoryIds.split(",")
            .map { it.trim().toLong() }

        Mediator.commands.send(
            UpdateCategorySortOrderCmd.Request(
                parentId = parentId,
                categoryIds = categoryIdList
            )
        )
        return AdminCategoryChangeSort.Response()
    }

    private fun qryResponseToApiResponse(node: GetCategoryTreeQry.Response): AdminCategoryLoad.Response {
        return AdminCategoryLoad.Response(
            categoryId = node.categoryId,
            categoryCode = node.code,
            categoryName = node.name,
            parentId = node.parentId,
            icon = node.icon,
            background = node.background,
            sort = node.sort.toInt(),
            children = node.children.map { qryResponseToApiResponse(it) }
        )
    }

}
