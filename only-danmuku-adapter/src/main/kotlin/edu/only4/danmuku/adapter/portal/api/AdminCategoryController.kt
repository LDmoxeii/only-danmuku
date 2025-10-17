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

    /**
     * 加载分类列表(树形结构)
     */
    @PostMapping("/loadCategory")
    fun adminCategoryLoad(@RequestBody request: AdminCategoryLoad.Request): AdminCategoryLoad.Response {
        // 根据请求参数决定是否转换为树形结构
        val response = if (request.convert2Tree == true) {
            // 获取树形结构
            val treeResult = Mediator.queries.send(GetCategoryTreeQry.Request())
            // 转换为前端需要的格式
            AdminCategoryLoad.Response(
                list = treeResult.categoryTree.map { convertTreeNode(it) }
            )
        } else {
            // 获取平铺列表 - Handler返回的是List<Response>
            val listResult = Mediator.queries.send(GetCategoryListQry.Request())
            // 转换为前端需要的格式
            AdminCategoryLoad.Response(
                list = listResult.map { convertCategoryInfo(it) }
            )
        }
        return response
    }

    /**
     * 保存/更新分类
     */
    @PostMapping("/saveCategory")
    fun adminCategorySave(@RequestBody @Validated request: AdminCategorySave.Request): AdminCategorySave.Response {
        // 判断是创建还是更新
        if (request.categoryId == null) {
            // 创建新分类
            Mediator.commands.send(
                CreateCategoryCmd.Request(
                    parentId = request.pCategoryId.toLong(),
                    code = request.categoryCode,
                    name = request.categoryName,
                    icon = request.icon,
                    background = request.background
                )
            )
        } else {
            // 更新已有分类
            Mediator.commands.send(
                UpdateCategoryInfoCmd.Request(
                    categoryId = request.categoryId.toLong(),
                    parentId = request.pCategoryId.toLong(),
                    code = request.categoryCode,
                    name = request.categoryName,
                    icon = request.icon,
                    background = request.background
                )
            )
        }
        return AdminCategorySave.Response()
    }

    /**
     * 删除分类
     */
    @PostMapping("/delCategory")
    fun adminCategoryDel(@RequestBody @Validated request: AdminCategoryDel.Request): AdminCategoryDel.Response {
        Mediator.commands.send(
            DeleteCategoryCmd.Request(
                categoryId = request.categoryId.toLong()
            )
        )
        return AdminCategoryDel.Response()
    }

    /**
     * 调整分类排序
     */
    @PostMapping("/changeSort")
    fun adminCategoryChangeSort(@RequestBody @Validated request: AdminCategoryChangeSort.Request): AdminCategoryChangeSort.Response {
        // 将分类ID字符串分割成列表
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

    /**
     * 转换树节点为通用Map格式
     */
    private fun convertTreeNode(node: GetCategoryTreeQry.CategoryTreeNode): Map<String, Any?> {
        return mapOf(
            "categoryId" to node.categoryId,
            "code" to node.code,
            "name" to node.name,
            "parentId" to node.parentId,
            "icon" to node.icon,
            "background" to node.background,
            "sort" to node.sort,
            "children" to node.children.map { convertTreeNode(it) }
        )
    }

    /**
     * 转换分类信息为通用Map格式
     */
    private fun convertCategoryInfo(info: GetCategoryListQry.Response): Map<String, Any?> {
        return mapOf(
            "categoryId" to info.categoryId,
            "code" to info.code,
            "name" to info.name,
            "parentId" to info.parentId,
            "icon" to info.icon,
            "background" to info.background,
            "sort" to info.sort
        )
    }

}
