package edu.only4.danmuku.application.commands.video_comment

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import org.springframework.stereotype.Service

/**
 * 批量删除评论
 */
object BatchDeleteCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 根据视频 ID 批量删除所有评论（包括主评论和回复）
            Mediator.repositories.remove(
                SVideoComment.predicate { it.videoId eq request.videoId }
            )

            Mediator.uow.save()

            return Response()
        }
    }

    data class Request(
        val videoId: Long,
    ) : RequestParam<Response>

    class Response
}

