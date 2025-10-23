package edu.only4.danmuku.application.commands.customer_action

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.CommentExists
import edu.only4.danmuku.application.validater.VideoExists
import edu.only4.danmuku.domain._share.meta.customer_action.SCustomerAction
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import edu.only4.danmuku.domain.aggregates.customer_action.factory.CustomerActionFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 点赞评论（与点踩互斥，支持取消）
 */
object LikeCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val likeActions = Mediator.repositories.find(
                SCustomerAction.predicate { s ->
                    s.all(
                        s.customerId eq request.customerId,
                        s.videoId eq request.videoId,
                        s.commentId eq request.commentId,
                        s.actionType eq ActionType.LIKE_COMMENT.code
                    )
                },
                persist = false
            )

            val hateActions = Mediator.repositories.find(
                SCustomerAction.predicate { s ->
                    s.all(
                        s.customerId eq request.customerId,
                        s.videoId eq request.videoId,
                        s.commentId eq request.commentId,
                        s.actionType eq ActionType.HATE_COMMENT.code
                    )
                },
                persist = false
            )

            val comment = Mediator.repositories.findOne(
                SVideoComment.predicateById(request.commentId),
                persist = false
            ).getOrNull() ?: throw KnownException("评论不存在")

            var isCancel = false
            var hadOpposite = false

            if (likeActions.isNotEmpty()) {
                likeActions.forEach(Mediator.uow::remove)
                isCancel = true
            } else {
                if (hateActions.isNotEmpty()) {
                    hateActions.forEach(Mediator.uow::remove)
                    hadOpposite = true
                }

                Mediator.factories.create(
                    CustomerActionFactory.Payload(
                        customerId = request.customerId.toString(),
                        videoId = request.videoId.toString(),
                        videoOwnerId = comment.videoOwnerId.toString(),
                        commentId = request.commentId,
                        actionType = ActionType.LIKE_COMMENT,
                        actionCount = 1
                    )
                )
            }

            Mediator.uow.save()
            return Response(isCancel = isCancel, hadOpposite = hadOpposite)
        }
    }

    data class Request(
        @field:VideoExists
        val videoId: Long,
        @field:CommentExists
        val commentId: Long,
        val customerId: Long,
    ) : RequestParam<Response>

    data class Response(
        val isCancel: Boolean,
        val hadOpposite: Boolean,
    )
}

