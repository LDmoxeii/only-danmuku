package edu.only4.danmuku.application.commands.category

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.CategoryMustExist
import edu.only4.danmuku.application.validator.UniqueCategoryCode
import edu.only4.danmuku.domain._share.meta.category.SCategory
import edu.only4.danmuku.domain.aggregates.category.Category
import org.springframework.stereotype.Service

/**
 * 更新分类信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateCategoryInfoCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val category = Mediator.repositories.findFirst(
                SCategory.predicateById(request.categoryId)
            ).get()

            category.updateBasicInfo(
                newName = request.name,
                newIcon = request.icon,
                newBackground = request.background
            )

            if (category.isParentChanged(request.parentId)) {
                if (category.isMovingToSelf(request.parentId)) {
                    throw KnownException("不能将分类移动到自身下")
                }

                val parentCategory: Category? = if (request.parentId != 0L) {
                    Mediator.repositories.findFirst(
                        SCategory.predicateById(request.parentId),
                        persist = false
                    ).get()
                } else null

                if (parentCategory != null && category.isMovingToDescendant(parentCategory)) {
                    throw KnownException("不能将分类移动到自己的子孙节点下")
                }

                val (oldPath, newPath) = category.changeParent(
                    newParentId = request.parentId,
                    parentCategory = parentCategory
                )


                val descendants: List<Category> = Mediator.repositories.find(
                    SCategory.predicate { it.nodePath like "${oldPath}%" }
                )
                descendants.forEach { it.rebaseNodePath(oldPath, newPath) }
            }

            if (category.isCodeChanged(request.code)) {
                category.changeCode(newCode = request.code)
            }

            Mediator.uow.save()
        }

    }

    @UniqueCategoryCode
    data class Request(
        @field:CategoryMustExist
        val categoryId: Long,
        @field:CategoryMustExist
        val parentId: Long = 0L,
        val code: String,
        val name: String,
        val icon: String? = null,
        val background: String? = null,
    ) : RequestParam<Unit>
}
