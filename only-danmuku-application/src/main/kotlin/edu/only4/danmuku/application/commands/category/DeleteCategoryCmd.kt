package edu.only4.danmuku.application.commands.category

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.category.SCategory
import edu.only4.danmuku.domain._share.meta.video.SVideo
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
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val childCategories = Mediator.repositories.find(
                SCategory.predicate { schema -> schema.parentId eq request.categoryId }
            )
            if (childCategories.isNotEmpty()) {
                throw KnownException("该分类下存在子分类，无法删除")
            }

            val videosUsingCategory = Mediator.repositories.find(
                SVideo.predicate { schema -> schema.categoryId eq request.categoryId }
            )
            if (videosUsingCategory.isNotEmpty()) {
                throw KnownException("该分类下存在关联视频，无法删除")
            }

            Mediator.repositories.remove(SCategory.predicateById(request.categoryId))

            Mediator.uow.save()

            return Response()
        }

    }

    data class Request(
        /** 要删除的分类ID */
        val categoryId: Long
    ) : RequestParam<Response>

    class Response
}
