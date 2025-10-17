package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

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
            // TODO: 实现更新分类排序逻辑
            // 1. 验证所有分类ID都属于同一父分类
            // 2. 按照categoryIds的顺序，更新每个分类的sort字段
            // 3. 批量保存更新

            Mediator.uow.save()

            return Response()
        }

    }

    data class Request(
        /** 父分类ID，确保只调整同一父分类下的子分类顺序 */
        val parentId: Long = 0L,
        /** 排序后的分类ID列表，按照新的显示顺序排列 */
        val categoryIds: List<Long>
    ) : RequestParam<Response>

    class Response
}
