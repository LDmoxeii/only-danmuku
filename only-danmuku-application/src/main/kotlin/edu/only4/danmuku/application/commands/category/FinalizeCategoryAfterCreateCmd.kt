package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.category.SCategory
import edu.only4.danmuku.domain._share.meta.or
import org.springframework.stereotype.Service

/**
 * 分类创建后的二阶段收尾：计算排序并基于ID回填节点路径
 */
object FinalizeCategoryAfterCreateCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val category = Mediator.repositories.findFirst(
                SCategory.predicateById(request.categoryId)
            ).get()

            // 读取父节点与同级节点
            val related = if (category.parentId != 0L) {
                Mediator.repositories.find(
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
            } else {
                Mediator.repositories.find(
                    SCategory.predicate(
                        {
                            it.all(
                                (it.id neq request.categoryId),
                                it.parentId eq 0L
                            )

                        },
                        { it.sort.asc() }
                    )
                )
            }

            val parent = related.find { it.id == category.parentId }
            val siblings = related.filter { it.parentId == category.parentId && it.id != category.id }

            // 确认目标排序；0 表示未设置，取（空=1，否则max+1）
            val targetSort: Byte = (
                if (category.sort.toInt() == 0) {
                    if (siblings.isEmpty()) 1 else (siblings.maxOf { it.sort } + 1).toByte()
                } else category.sort
            )

            // 若显式要求插入到某序号，右移同级节点
            if (siblings.isNotEmpty()) {
                val affected = siblings.filter { it.sort >= targetSort }
                affected.forEach { it.addSort(1) }
            }

            // 更新自身排序
            category.sort = targetSort

            // 基于ID回填路径
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

