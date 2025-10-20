package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.UniqueCategoryCode
import edu.only4.danmuku.domain._share.meta.category.SCategory
import edu.only4.danmuku.domain._share.meta.or
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
            // 1. 一次查询获取父节点和所有同级节点
            val relatedCategories = if (request.parentId != 0L) {
                // 查询：父节点 OR 同级节点
                Mediator.repositories.find(
                    SCategory.predicate(
                        { schema ->
                            (schema.id eq request.parentId) or (schema.parentId eq request.parentId)
                        },
                        { schema -> schema.sort.asc() }
                    )
                )
            } else {
                // 根节点：只查询所有顶级分类
                Mediator.repositories.find(
                    SCategory.predicate(
                        { it.parentId eq 0L },
                        { it.sort.asc() }
                    )
                )
            }

            // 分离父节点和同级节点
            val parentCategory = relatedCategories.find { it.id == request.parentId }
            val siblings = relatedCategories.filter { it.parentId == request.parentId }

            // 验证父节点存在（如果需要）
            if (request.parentId != 0L && parentCategory == null) {
                throw IllegalArgumentException("父分类不存在：${request.parentId}")
            }

            val targetSort: Byte = when {
                request.sort != null -> request.sort
                siblings.isEmpty() -> 1
                else -> (siblings.maxOf { it.sort } + 1).toByte()
            }

            // 4. 移动受影响的同级节点
            if (request.sort != null && siblings.isNotEmpty()) {
                // 找出所有需要移动的同级节点（sort >= targetSort）
                // 直接在内存中筛选，避免额外的数据库查询
                val affectedSiblings = siblings.filter { it.sort >= targetSort }

                // 批量更新 sort 值
                // 注意：只需要更新同级节点的 sort，子树的 sort 不受影响
                // 因为 sort 是相对于同级兄弟的，不同层级的 sort 互不影响
                affectedSiblings.forEach { sibling ->
                    sibling.addSort(1)
                }
            }

            // 5. 创建新分类
            val category = Mediator.factories.create(
                CategoryFactory.Payload(
                    parentId = request.parentId,
                    code = request.code,
                    name = request.name,
                    icon = request.icon,
                    background = request.background,
                    sort = targetSort
                )
            )

            // 6. 设置节点路径
            val parentPath = parentCategory?.nodePath ?: ""
            category.updateNodePath(parentPath)

            // 7. 保存所有变更
            Mediator.uow.save()

            return Response(
                categoryId = category.id
            )
        }

    }

    data class Request(
        /** 父分类ID，顶级分类传0 */
        val parentId: Long = 0L,
        /** 分类编码，唯一标识 */
        @UniqueCategoryCode
        val code: String,
        /** 分类名称 */
        val name: String,
        /** 图标路径或URL */
        val icon: String? = null,
        /** 背景图路径或URL */
        val background: String? = null,
        /** 排序号，同级分类中的显示顺序 */
        val sort: Byte? = null,
    ) : RequestParam<Response>

    data class Response(
        /** 新创建的分类ID */
        val categoryId: Long
    )
}
