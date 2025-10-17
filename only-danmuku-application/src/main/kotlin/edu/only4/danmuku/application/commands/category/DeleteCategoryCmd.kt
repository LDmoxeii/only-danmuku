package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

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
            // TODO: 实现删除分类逻辑
            // 1. 根据ID查找分类聚合根
            // 2. 检查该分类下是否有子分类，如有则不允许删除
            // 3. 检查是否有视频使用该分类，如有则不允许删除
            // 4. 执行软删除(设置deleted标记)

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
