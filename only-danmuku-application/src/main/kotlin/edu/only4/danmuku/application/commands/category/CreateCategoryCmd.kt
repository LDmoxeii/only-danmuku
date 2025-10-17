package edu.only4.danmuku.application.commands.category

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

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
            // TODO: 实现创建分类逻辑
            // 1. 检查分类编码是否已存在
            // 2. 创建分类聚合根
            // 3. 计算并设置节点路径(nodePath)
            // 4. 保存分类

            Mediator.uow.save()

            return Response(
                categoryId = 0L // TODO: 返回新创建的分类ID
            )
        }

    }

    data class Request(
        /** 父分类ID，顶级分类传0 */
        val parentId: Long = 0L,
        /** 分类编码，唯一标识 */
        val code: String,
        /** 分类名称 */
        val name: String,
        /** 图标路径或URL */
        val icon: String? = null,
        /** 背景图路径或URL */
        val background: String? = null,
        /** 排序号，同级分类中的显示顺序 */
        val sort: Byte = 0
    ) : RequestParam<Response>

    data class Response(
        /** 新创建的分类ID */
        val categoryId: Long
    )
}
