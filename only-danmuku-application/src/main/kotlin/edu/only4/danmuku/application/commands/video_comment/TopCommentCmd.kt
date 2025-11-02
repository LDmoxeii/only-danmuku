package edu.only4.danmuku.application.commands.video_comment

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.CommentExists
import edu.only4.danmuku.application.validator.VideoCommentOwner
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import edu.only4.danmuku.domain.aggregates.video_comment.VideoComment

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 置顶评论
 */
object TopCommentCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val comment = Mediator.repositories.findOne(
                SVideoComment.predicateById(request.commentId)
            ).getOrNull() ?: throw KnownException("评论不存在：${request.commentId}")

            val existingTopComments = Mediator.repositories.find(
                SVideoComment.predicate { schema ->
                    schema.all(
                        schema.videoId eq comment.videoId,
                        schema.topType eq 1.toByte(),
                        schema.id neq comment.id
                    )
                }
            )

            existingTopComments.forEach(VideoComment::untop)
            comment.top()

            Mediator.uow.save()
        }
    }

    @VideoCommentOwner
    data class Request(
        /** 评论ID */
        @field:CommentExists
        val commentId: Long,
        /** 操作用户ID，null 表示后台管理员 */
        val operatorId: Long? = null,
    ) : RequestParam<Unit>
}
