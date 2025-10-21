package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import edu.only4.danmuku.application.validater.UniqueCategoryCode
import edu.only4.danmuku.application.validater.UniqueCategoryCodeTarget
import edu.only4.danmuku.domain._share.meta.category.SCategory
import edu.only4.danmuku.domain.aggregates.category.Category
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新分类信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateCategoryInfoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val category = Mediator.repositories.findFirst(
                SCategory.predicateById(request.categoryId)
            ).get()

            category.updateBasicInfo(
                newName = request.name,
                newIcon = request.icon,
                newBackground = request.background
            )

            val parentChanged = category.isParentChanged(request.parentId)
            var parentCategory: Category? = null
            var pathChange: Pair<String, String>? = null

            if (parentChanged) {
                if (category.isMovingToSelf(request.parentId)) {
                    throw IllegalArgumentException("不能将分类移动到自身下")
                }

                if (request.parentId != 0L) {
                    parentCategory = Mediator.repositories.findFirst(
                        SCategory.predicateById(request.parentId),
                        persist = false
                    ).getOrNull() ?: throw IllegalArgumentException("父分类不存在：${request.parentId}")

                    if (category.isMovingToDescendant(parentCategory)) {
                        throw IllegalArgumentException("不能将分类移动到自己的子孙节点下")
                    }
                }
            }

            if (parentChanged) {
                pathChange = category.changeParent(
                    newParentId = request.parentId,
                    parentCategory = parentCategory
                )
            }

            val codeChanged = category.isCodeChanged(request.code)
            var allDescendants: List<Category> = emptyList()

            if (parentChanged || codeChanged) {
                allDescendants = Mediator.repositories.find(
                    JpaPredicate.bySpecification(
                        Category::class.java,
                        SCategory.specify { it.nodePath like "${category.nodePath}%" }
                    )
                )
            }

            if (codeChanged) {
                pathChange = category.changeCode(newCode = request.code)
            }

            pathChange?.let { (oldPath, newPath) ->
                allDescendants.forEach { descendant ->
                    descendant.nodePath = descendant.nodePath.replaceFirst(oldPath, newPath)
                }
            }

            Mediator.uow.save()

            return Response()
        }

    }

    @UniqueCategoryCode
    data class Request(
        /** 分类ID */
        override val categoryId: Long,
        /** 父分类ID */
        val parentId: Long = 0L,
        /** 分类编码 */
        override val code: String,
        /** 分类名称 */
        val name: String,
        /** 图标路径或URL */
        val icon: String? = null,
        /** 背景图路径或URL */
        val background: String? = null
    ) : RequestParam<Response>, UniqueCategoryCodeTarget

    class Response
}
