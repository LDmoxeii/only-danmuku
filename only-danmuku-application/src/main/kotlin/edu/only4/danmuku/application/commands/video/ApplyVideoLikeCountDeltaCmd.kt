package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 调整视频点赞数
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/23
 */
object ApplyVideoLikeCountDeltaCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId),
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            val appliedDelta = video.applyLikeCountDelta(request.delta)

            Mediator.uow.save()

            return Response(
                videoId = video.id,
                likeCount = video.likeCount,
                appliedDelta = appliedDelta
            )
        }
    }

    data class Request(
        val videoId: Long,
        val delta: Int = 1
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
        val likeCount: Int,
        val appliedDelta: Int
    )
}
