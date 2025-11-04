package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.CategoryLoadAll
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SaIgnore
@RestController
@RequestMapping("/category")
@Validated
class CompatibleCategoryController {

    @PostMapping("/loadAllCategory")
    fun getCategoryTree(): List<CategoryLoadAll.Response> {
        val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
        return treeResult.map { CategoryLoadAll.Converter.INSTANCE.fromApp(it) }

    }
}
