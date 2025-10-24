package edu.only4.danmuku.application.commands.video_play_history

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_play_history.SVideoPlayHistory
import edu.only4.danmuku.domain.aggregates.video_play_history.factory.VideoPlayHistoryFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 添加播放记录
 */
object AddPlayHistoryCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 查找是否已有该用户对该视频的历史记录
            val existing = Mediator.repositories.findFirst(
                SVideoPlayHistory.predicate { schema ->
                    schema.all(
                        schema.customerId eq request.customerId,
                        schema.videoId eq request.videoId,
                    )
                },
            ).getOrNull()

            val now = System.currentTimeMillis() / 1000

            if (existing != null) {
                existing.fileIndex = request.fileIndex
                existing.updateTime = now
            } else {
                Mediator.factories.create(
                    VideoPlayHistoryFactory.Payload(
                        customerId = request.customerId,
                        videoId = request.videoId,
                        fileIndex = request.fileIndex,
                        createTime = now,
                        updateTime = now
                    )
                )
            }

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 用户ID */
        val customerId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 文件索引(P) */
        val fileIndex: Int,
    ) : RequestParam<Response>

    class Response
}
