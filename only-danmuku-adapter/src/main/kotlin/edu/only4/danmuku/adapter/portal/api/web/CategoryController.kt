package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.category.GetCategoryTree
import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SaIgnore
@RestController
@RequestMapping("/category")
class CategoryController {

    @PostMapping("/getCategoryTree")
    fun getCategoryTree(): List<GetCategoryTree.Item> {
        val treeResult = Mediator.qry.send(GetCategoryTreeQry.Request())
        return treeResult.map { GetCategoryTree.Converter.INSTANCE.fromApp(it) }

    }
}
