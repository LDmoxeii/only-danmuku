package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.CategoryExists
import edu.only4.danmuku.application.validater.UniqueCategoryCode
import edu.only4.danmuku.application.validater.UniqueCategoryCodeTarget
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
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val initialSort: Byte = request.sort ?: 0

            val category = Mediator.factories.create(
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

            return Response(
                categoryId = category.id
            )
        }

    }

    @UniqueCategoryCode
    data class Request(
        /** 父分类ID，顶级分类传0 */
        @field:CategoryExists
        val parentId: Long = 0L,
        /** 分类编码，唯一标识 */
        override val code: String,
        /** 分类名称 */
        val name: String,
        /** 图标路径或URL */
        val icon: String? = null,
        /** 背景图路径或URL */
        val background: String? = null,
        /** 排序号，同级分类中的显示顺序 */
        val sort: Byte? = null,
    ) : RequestParam<Response>, UniqueCategoryCodeTarget {
        override val categoryId: Long? = null
    }

    data class Response(
        /** 新创建的分类ID */
        val categoryId: Long
    )
}
