package edu.only4.danmuku.application.commands.video_comment

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.CommentDeletePermission
import edu.only4.danmuku.application.validater.CommentExists
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
            val comment = Mediator.repositories.findFirst(
                SVideoComment.predicateById(request.commentId),
                persist = false
            ).getOrNull() ?: return Response()

            // 删除评论（软删）
            Mediator.repositories.remove(SVideoComment.predicateById(request.commentId))

            if (comment.parentId == 0L) {
                Mediator.repositories.remove(
                    SVideoComment.predicate { schema ->
                        schema.parentId eq comment.id
                    }
                )
            }

            Mediator.uow.save()

            return Response()
        }
    }

    @CommentDeletePermission
    data class Request(
        /** 评论ID */
        @field:CommentExists
        val commentId: Long,
        /** 操作者ID；null 表示管理员 */
        val operatorId: Long? = null,
    ) : RequestParam<Response>

    class Response
}
