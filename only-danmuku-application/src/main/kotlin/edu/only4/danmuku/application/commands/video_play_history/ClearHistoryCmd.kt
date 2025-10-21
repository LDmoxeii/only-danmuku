package edu.only4.danmuku.application.commands.video_play_history

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_play_history.SVideoPlayHistory

import org.springframework.stereotype.Service

/**
 * 清空播放记录
 */
object ClearHistoryCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 删除当前用户的全部播放历史（软删）
            Mediator.repositories.remove(
                SVideoPlayHistory.predicate { it.customerId eq request.customerId }
            )

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 用户ID */
        val customerId: Long,
    ) : RequestParam<Response>

    class Response
}
