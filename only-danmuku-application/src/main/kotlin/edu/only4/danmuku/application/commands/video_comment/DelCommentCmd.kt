package edu.only4.danmuku.application.commands.video_comment

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

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
            // 校验评论存在
            val comment = Mediator.repositories.findFirst(
                SVideoComment.predicateById(request.commentId),
                persist = false
            ).getOrNull() ?: throw KnownException("评论不存在：${request.commentId}")

            // 删除评论（软删）
            Mediator.repositories.remove(SVideoComment.predicateById(comment.id))

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
