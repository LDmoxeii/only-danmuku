package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

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
            // TODO: 实现更新分类信息逻辑
            // 1. 根据ID查找分类聚合根
            // 2. 检查新编码是否与其他分类冲突
            // 3. 更新分类信息
            // 4. 如果父分类变更，需要重新计算节点路径(nodePath)
            // 5. 保存更新

            Mediator.uow.save()

            return Response()
        }

    }

    data class Request(
        /** 分类ID */
        val categoryId: Long,
        /** 父分类ID */
        val parentId: Long = 0L,
        /** 分类编码 */
        val code: String,
        /** 分类名称 */
        val name: String,
        /** 图标路径或URL */
        val icon: String? = null,
        /** 背景图路径或URL */
        val background: String? = null
    ) : RequestParam<Response>

    class Response
}
