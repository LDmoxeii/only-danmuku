package edu.only4.danmuku.application.commands.video_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object TranscodeAllTranscodingFilesCmd {

    @Service
    class Handler : Command<Request, Response> {

        override fun exec(request: Request): Response {
            val videoPost = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoPostId)
            ).getOrNull() ?: throw KnownException("视频不存在")

            // 转码职责已迁移至防腐层 CLI，当前命令仅占位返回
            Mediator.uow.persist(videoPost)
            Mediator.uow.save()
            return Response(total = 0, success = 0, failed = 0)
        }
    }

    data class Request(
        val videoPostId: Long,
    ) : RequestParam<Response>

    data class Response(
        val total: Int,
        val success: Int,
        val failed: Int,
    )
}
