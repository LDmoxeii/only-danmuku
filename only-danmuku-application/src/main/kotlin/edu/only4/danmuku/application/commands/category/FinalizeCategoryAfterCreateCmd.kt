package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.category.SCategory
import edu.only4.danmuku.domain._share.meta.or
import org.springframework.stereotype.Service

object FinalizeCategoryAfterCreateCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val category = Mediator.repositories.findOne(
                SCategory.predicateById(request.categoryId)
            ).get()

            val related = Mediator.repositories.find(
                SCategory.predicate(
                    { schema ->
                        schema.all(
                            (schema.id neq request.categoryId),
                            (schema.id eq category.parentId) or (schema.parentId eq category.parentId)
                        )
                    },
                    { schema -> schema.sort.asc() }
                )
            )

            val parent = related.find { it.id == category.parentId }
            val siblings = related.filter { it.parentId == category.parentId && it.id != category.id }

            val targetSort: Byte = (
                if (category.sort.toInt() == 0) {
                    if (siblings.isEmpty()) 1 else (siblings.maxOf { it.sort } + 1).toByte()
                } else category.sort
            )

            if (siblings.isNotEmpty()) {
                val affected = siblings.filter { it.sort >= targetSort }
                affected.forEach { it.addSort(1) }
            }

            category.sort = targetSort

            val parentPath = parent?.nodePath ?: ""
            category.updateNodePath(parentPath)

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val categoryId: Long
    ) : RequestParam<Response>

    class Response
}

