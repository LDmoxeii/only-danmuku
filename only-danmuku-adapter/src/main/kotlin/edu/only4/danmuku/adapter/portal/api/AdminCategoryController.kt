package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminCategoryLoad
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
    fun adminCategoryLoad(@RequestBody request: AdminCategoryLoad.Request): List<AdminCategoryLoad.Response> {
        // 根据请求参数决定是否转换为树形结构
        return if (request.convert2Tree == true) {
            // 获取树形结构
            val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
            // 转换为前端需要的格式
            treeResult.map { qryResponseToApiResponse(it) }
        } else emptyList()
    }
//
//    /**
//     * 保存/更新分类
//     */
//    @PostMapping("/saveCategory")
//    fun adminCategorySave(@RequestBody @Validated request: AdminCategorySave.Request): AdminCategorySave.Response {
//        // 判断是创建还是更新
//        if (request.categoryId == null) {
//            // 创建新分类
//            Mediator.commands.send(
//                CreateCategoryCmd.Request(
//                    parentId = request.pCategoryId.toLong(),
//                    code = request.categoryCode,
//                    name = request.categoryName,
//                    icon = request.icon,
//                    background = request.background
//                )
//            )
//        } else {
//            // 更新已有分类
//            Mediator.commands.send(
//                UpdateCategoryInfoCmd.Request(
//                    categoryId = request.categoryId.toLong(),
//                    parentId = request.pCategoryId.toLong(),
//                    code = request.categoryCode,
//                    name = request.categoryName,
//                    icon = request.icon,
//                    background = request.background
//                )
//            )
//        }
//        return AdminCategorySave.Response()
//    }
//
//    /**
//     * 删除分类
//     */
//    @PostMapping("/delCategory")
//    fun adminCategoryDel(@RequestBody @Validated request: AdminCategoryDel.Request): AdminCategoryDel.Response {
//        Mediator.commands.send(
//            DeleteCategoryCmd.Request(
//                categoryId = request.categoryId.toLong()
//            )
//        )
//        return AdminCategoryDel.Response()
//    }
//
//    /**
//     * 调整分类排序
//     */
//    @PostMapping("/changeSort")
//    fun adminCategoryChangeSort(@RequestBody @Validated request: AdminCategoryChangeSort.Request): AdminCategoryChangeSort.Response {
//        // 将分类ID字符串分割成列表
//        val categoryIdList = request.categoryIds.split(",")
//            .map { it.trim().toLong() }
//
//        Mediator.commands.send(
//            UpdateCategorySortOrderCmd.Request(
//                parentId = request.pCategoryId.toLong(),
//                categoryIds = categoryIdList
//            )
//        )
//        return AdminCategoryChangeSort.Response()
//    }
//
    /**
     * 转换树节点为强类型 CategoryItem
     */
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
//
//    /**
//     * 转换分类信息为强类型 CategoryItem
//     */
//    private fun convertCategoryInfo(info: GetCategoryListQry.Response): AdminCategoryLoad.Response {
//        return AdminCategoryLoad.Response(
//            categoryId = info.categoryId,
//            code = info.code,
//            name = info.name,
//            parentId = info.parentId,
//            icon = info.icon,
//            background = info.background,
//            sort = info.sort
//        )
//    }

}
