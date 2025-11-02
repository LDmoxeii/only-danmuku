package edu.only4.danmuku.application.commands.video_comment

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.CommentDeletePermission
import edu.only4.danmuku.application.validator.CommentExists
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
object DeleteVideoCommentCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val comment = Mediator.repositories.findOne(
                SVideoComment.predicateById(request.commentId),
                persist = false
            ).getOrNull() ?: return

            Mediator.uow.remove(comment)

            if (comment.parentId == 0L) {
                Mediator.repositories.remove(
                    SVideoComment.predicate { schema ->
                        schema.parentId eq comment.id
                    }
                )
            }

            Mediator.uow.save()
        }
    }

    @CommentDeletePermission
    data class Request(
        @field:CommentExists
        val commentId: Long,
        val operatorId: Long? = null,
    ) : RequestParam<Unit>
}
