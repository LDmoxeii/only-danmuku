package edu.only4.danmuku.application.commands.video_play_history

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_play_history.SVideoPlayHistory

import org.springframework.stereotype.Service

/**
 * 删除播放记录
 */
object DelHistoryCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 删除当前用户某个视频的播放历史（可能多条分片记录）
            Mediator.repositories.remove(
                SVideoPlayHistory.predicate { schema ->
                    schema.all(
                        schema.customerId eq request.customerId,
                        schema.videoId eq request.videoId,
                    )
                }
            )

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 用户ID */
        val customerId: Long,
        /** 视频ID */
        val videoId: Long,
    ) : RequestParam<Response>

    class Response
}
