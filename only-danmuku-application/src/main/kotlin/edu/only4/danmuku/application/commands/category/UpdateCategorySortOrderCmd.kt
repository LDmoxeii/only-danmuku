package edu.only4.danmuku.application.commands.category

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.CategoryMustExist
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
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val categories = Mediator.repositories.find(
                SCategory.predicateByIds(request.categoryIds)
            )

            // 校验：全部存在
            if (categories.size != request.categoryIds.toSet().size) {
                throw KnownException("存在无效的分类ID，无法完成排序")
            }

            val invalidParent = categories.any { !it.isDirectChildOf(request.parentId) }
            if (invalidParent) {
                throw KnownException("仅允许调整同一父分类下的子分类顺序")
            }

            // 按 ID 建立索引，便于按请求顺序更新
            val byId = categories.associateBy { it.id }

            // 按照传入顺序设置 sort，从 1 开始递增
            var sortNo = 1
            request.categoryIds.forEach { id ->
                val category = byId[id]!!

                category.sort = sortNo.toByte()
                sortNo += 1
            }

            Mediator.uow.save()

            return Response()
        }

    }

    data class Request(
        /** 父分类ID，确保只调整同一父分类下的子分类顺序 */
        @field:CategoryMustExist
        val parentId: Long = 0L,
        /** 排序后的分类ID列表，按照新的显示顺序排列 */
        @field:NotEmpty(message = "分类ID列表不能为空")
        val categoryIds: List<Long>,
    ) : RequestParam<Response>

    class Response
}
