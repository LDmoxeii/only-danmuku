package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.CategoryLoadAll
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
     * 加载所有分类
     */
    @GetMapping("/loadAllCategory")
    fun categoryLoadAll(): CategoryLoadAll.Response {
        // TODO: 实现加载所有分类逻辑
        return CategoryLoadAll.Response()
    }

}
