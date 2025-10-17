package edu.only4.danmuku.application.commands.video_comment

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 删除评论
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object DelCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // TODO: 实现删除评论逻辑
            Mediator.uow.save()

            return Response()
        }
    }

    data class Request(
        /** 评论ID */
        val commentId: Long
    ) : RequestParam<Response>

    class Response
}
