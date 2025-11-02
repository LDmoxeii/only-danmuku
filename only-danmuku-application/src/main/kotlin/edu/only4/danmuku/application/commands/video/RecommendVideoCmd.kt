package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 切换视频推荐状态
 */
object RecommendVideoCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val video = Mediator.repositories.findOne(
                SVideo.predicate { it.videoPostId eq request.videoId }
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            // 切换推荐状态（推荐 <-> 未推荐）
            video.toggleRecommend()

            Mediator.uow.save()
        }
    }

    data class Request(
        val videoId: Long
    ) : RequestParam<Unit>
}
