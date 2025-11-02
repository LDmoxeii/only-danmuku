package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.CategoryDeletionAllowed
import edu.only4.danmuku.application.validator.CategoryMustExist
import edu.only4.danmuku.domain._share.meta.category.SCategory
import org.springframework.stereotype.Service

/**
 * 删除分类
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object DeleteCategoryCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val token = "/${request.categoryId}/"
            Mediator.repositories.remove(
                SCategory.predicate { schema ->
                    schema.nodePath like "%$token%"
                }
            )

            Mediator.uow.save()
        }

    }

    data class Request(
        @field:CategoryMustExist
        @field:CategoryDeletionAllowed
        val categoryId: Long,
    ) : RequestParam<Unit>
}
