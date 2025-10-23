package edu.only4.danmuku.application.commands.video_comment

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import org.springframework.stereotype.Service

/** 将“用户点赞评论”事件落地到评论统计 */
object ApplyCustomerLikedCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val comment = Mediator.Companion.repositories.findFirst(
                SVideoComment.Companion.predicateById(request.commentId)
            ).get()
            comment.updateStatistics(likeChange = +1, hateChange = 0)
            Mediator.Companion.uow.save()
            return Response()
        }
    }

    data class Request(
        val commentId: Long,
    ) : RequestParam<Response>

    class Response
}
