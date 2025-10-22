package edu.only4.danmuku.application.commands.category

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import edu.only4.danmuku.application.validater.UniqueCategoryCode
import edu.only4.danmuku.application.validater.ParentCategoryExists
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

            if (category.isParentChanged(request.parentId)) {
                if (category.isMovingToSelf(request.parentId)) {
                    throw KnownException("不能将分类移动到自身下")
                }

                val parentCategory: Category? = if (request.parentId != 0L) {
                    Mediator.repositories.findFirst(
                        SCategory.predicateById(request.parentId),
                        persist = false
                    ).getOrNull() ?: throw KnownException("父分类不存在：${request.parentId}")
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
                // 路径基于ID，编码变更不再影响路径
                category.changeCode(newCode = request.code)
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
        @ParentCategoryExists
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
