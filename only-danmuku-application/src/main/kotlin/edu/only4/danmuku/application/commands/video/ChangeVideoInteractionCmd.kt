package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 修改视频互动设置
 */
object ChangeVideoInteractionCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val video = Mediator.Companion.repositories.findOne(
                SVideo.predicate { it.videoPostId eq request.videoId },
            ).getOrNull() ?: return

            if (video.customerId != request.userId) {
                throw KnownException("无权限修改该视频互动设置")
            }

            video.changeInteraction(request.interaction)

            Mediator.Companion.uow.save()
        }
    }

    data class Request(
        /** 视频ID */
        val videoId: Long,
        /** 用户ID */
        val userId: Long,
        /** 互动设置 */
        val interaction: String?,
    ) : RequestParam<Unit>
}
