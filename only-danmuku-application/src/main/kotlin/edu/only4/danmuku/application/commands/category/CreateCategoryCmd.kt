package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.CategoryMustExist
import edu.only4.danmuku.application.validator.UniqueCategoryCode
import edu.only4.danmuku.domain.aggregates.category.factory.CategoryFactory
import org.springframework.stereotype.Service

/**
 * 创建分类
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object CreateCategoryCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val initialSort: Byte = request.sort ?: 0

            Mediator.factories.create(
                CategoryFactory.Payload(
                    parentId = request.parentId,
                    code = request.code,
                    name = request.name,
                    icon = request.icon,
                    background = request.background,
                    sort = initialSort
                )
            )

            Mediator.uow.save()
        }

    }

    @UniqueCategoryCode
    data class Request(
        @field:CategoryMustExist
        val parentId: Long = 0L,
        val code: String,
        val name: String,
        val icon: String? = null,
        val background: String? = null,
        val sort: Byte? = null,
    ) : RequestParam<Unit>
}
