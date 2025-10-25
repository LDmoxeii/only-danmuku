package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryChangeSort
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryDel
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryLoad
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategorySave
import edu.only4.danmuku.application.commands.category.CreateCategoryCmd
import edu.only4.danmuku.application.commands.category.DeleteCategoryCmd
import edu.only4.danmuku.application.commands.category.UpdateCategoryInfoCmd
import edu.only4.danmuku.application.commands.category.UpdateCategorySortOrderCmd
import edu.only4.danmuku.application.queries.category.GetCategoryListQry
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
@Validated
class AdminCategoryController {

    @PostMapping("/loadCategory")
    fun adminCategoryLoad(@RequestBody request: AdminCategoryLoad.Request): List<AdminCategoryLoad.Response> {
        return if (request.convert2Tree == true) {
            val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
            treeResult.map { qryResponseToApiResponse(it) }
        } else {
            val listResult = Mediator.queries.send(GetCategoryListQry.Request())
            listResult.map {
                AdminCategoryLoad.Response(
                    categoryId = it.categoryId,
                    code = it.code,
                    name = it.name,
                    parentId = it.parentId,
                    icon = it.icon,
                    background = it.background,
                    sort = it.sort
                )
            }
        }
    }


    @PostMapping("/saveCategory")
    fun adminCategorySave(@RequestBody @Validated request: AdminCategorySave.Request): AdminCategorySave.Response {
        when (request.categoryId) {
            null -> {
                Mediator.commands.send(
                    CreateCategoryCmd.Request(
                        parentId = request.pCategoryId,
                        code = request.categoryCode,
                        name = request.categoryName,
                        icon = request.icon,
                        background = request.background
                    )
                )
            }

            else -> {
                Mediator.commands.send(
                    UpdateCategoryInfoCmd.Request(
                        categoryId = request.categoryId,
                        parentId = request.pCategoryId,
                        code = request.categoryCode,
                        name = request.categoryName,
                        icon = request.icon,
                        background = request.background
                    )
                )
            }
        }
        return AdminCategorySave.Response()
    }

    @PostMapping("/delCategory")
    fun adminCategoryDel(@RequestBody @Validated request: AdminCategoryDel.Request): AdminCategoryDel.Response {
        Mediator.commands.send(
            DeleteCategoryCmd.Request(
                categoryId = request.categoryId
            )
        )
        return AdminCategoryDel.Response()
    }

    @PostMapping("/changeSort")
    fun adminCategoryChangeSort(@RequestBody @Validated request: AdminCategoryChangeSort.Request): AdminCategoryChangeSort.Response {
        val categoryIdList = request.categoryIds.split(",")
            .map { it.trim().toLong() }

        Mediator.commands.send(
            UpdateCategorySortOrderCmd.Request(
                parentId = request.pCategoryId.toLong(),
                categoryIds = categoryIdList
            )
        )
        return AdminCategoryChangeSort.Response()
    }

    private fun qryResponseToApiResponse(node: GetCategoryTreeQry.Response): AdminCategoryLoad.Response {
        return AdminCategoryLoad.Response(
            categoryId = node.categoryId,
            code = node.code,
            name = node.name,
            parentId = node.parentId,
            icon = node.icon,
            background = node.background,
            sort = node.sort,
            children = node.children.map { qryResponseToApiResponse(it) }
        )
    }

}
