package edu.only4.danmuku.application.commands.video_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object ChangeVideoPostInteractionCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {

            val videoPost = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoId)
            ).getOrNull() ?: throw KnownException("视频草稿不存在：${request.videoId}")

            if (videoPost.customerId != request.userId) {
                throw KnownException("无权限修改该视频互动设置")
            }

            videoPost.changeInteraction(request.interaction)

            Mediator.uow.save()
        }
    }

    data class Request(
        val videoId: Long,
        val userId: Long,
        val interaction: String?,
    ) : RequestParam<Unit>
}
