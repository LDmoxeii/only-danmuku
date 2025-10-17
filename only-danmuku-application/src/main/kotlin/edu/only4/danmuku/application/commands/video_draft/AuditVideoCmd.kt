package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 审核视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object AuditVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // TODO: 实现审核视频业务逻辑
            Mediator.uow.save()

            return Response()
        }

    }

    data class Request(
        /** 视频ID */
        val videoId: Long,
        /** 审核状态: 0-审核中 1-审核通过 2-审核不通过 */
        val status: Int,
        /** 审核原因 */
        val reason: String? = null
    ) : RequestParam<Response>

    class Response(
    )
}
