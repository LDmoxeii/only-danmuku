package edu.only4.danmuku.application.commands.video_comment

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import org.springframework.stereotype.Service

/** 将“用户点踩评论”事件落地到评论统计 */
object ApplyCustomerDislikedCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val comment = Mediator.repositories.findFirst(
                SVideoComment.predicateById(request.commentId)
            ).get()
            comment.updateStatistics(likeChange = 0, hateChange = +1)
            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val commentId: Long,
    ) : RequestParam<Response>

    class Response
}
