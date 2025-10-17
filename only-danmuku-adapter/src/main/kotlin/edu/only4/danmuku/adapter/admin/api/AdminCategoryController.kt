package edu.only4.danmuku.adapter.admin.api

import edu.only4.danmuku.adapter.admin.api.payload.AdminCategoryChangeSort
import edu.only4.danmuku.adapter.admin.api.payload.AdminCategoryDel
import edu.only4.danmuku.adapter.admin.api.payload.AdminCategoryLoad
import edu.only4.danmuku.adapter.admin.api.payload.AdminCategorySave
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
        // TODO: 实现加载分类列表(树形结构)逻辑
        return AdminCategoryLoad.Response()
    }

    /**
     * 保存/更新分类
     */
    @PostMapping("/saveCategory")
    fun adminCategorySave(@RequestBody @Validated request: AdminCategorySave.Request): AdminCategorySave.Response {
        // TODO: 实现保存/更新分类逻辑
        return AdminCategorySave.Response()
    }

    /**
     * 删除分类
     */
    @PostMapping("/delCategory")
    fun adminCategoryDel(@RequestBody @Validated request: AdminCategoryDel.Request): AdminCategoryDel.Response {
        // TODO: 实现删除分类逻辑
        return AdminCategoryDel.Response()
    }

    /**
     * 调整分类排序
     */
    @PostMapping("/changeSort")
    fun adminCategoryChangeSort(@RequestBody @Validated request: AdminCategoryChangeSort.Request): AdminCategoryChangeSort.Response {
        // TODO: 实现调整分类排序逻辑
        return AdminCategoryChangeSort.Response()
    }

}
