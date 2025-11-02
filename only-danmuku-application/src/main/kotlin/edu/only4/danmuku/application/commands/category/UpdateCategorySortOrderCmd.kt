package edu.only4.danmuku.application.commands.category

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.CategoryMustExist
import edu.only4.danmuku.domain._share.meta.category.SCategory
import jakarta.validation.constraints.NotEmpty
import org.springframework.stereotype.Service

/**
 * 更新分类排序
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateCategorySortOrderCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val categories = Mediator.repositories.find(
                SCategory.predicateByIds(request.categoryIds)
            )

            if (categories.size != request.categoryIds.toSet().size) {
                throw KnownException("存在无效的分类ID，无法完成排序")
            }

            val invalidParent = categories.any { !it.isDirectChildOf(request.parentId) }
            if (invalidParent) {
                throw KnownException("仅允许调整同一父分类下的子分类顺序")
            }

            val byId = categories.associateBy { it.id }

            var sortNo = 1
            request.categoryIds.forEach { id ->
                val category = byId[id]!!

                category.changeSort(sortNo.toByte())
                sortNo += 1
            }

            Mediator.uow.save()
        }

    }

    data class Request(
        @field:CategoryMustExist
        val parentId: Long = 0L,
        @field:NotEmpty(message = "分类ID列表不能为空")
        val categoryIds: List<Long>,
    ) : RequestParam<Unit>
}
