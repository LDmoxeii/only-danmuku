package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_hls_key_token.SVideoHlsKeyToken
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 审核通过后补充 videoId（token 转正绑定）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object BindVideoHlsKeyTokenToVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val token = Mediator.repositories.findOne(
                SVideoHlsKeyToken.predicateById(request.tokenId)
            ).getOrNull() ?: throw KnownException("播放 token 不存在: ${request.tokenId}")

            token.bindVideoId(request.videoId)
            Mediator.uow.save()

            return Response(
                success = true
            )
        }

    }

    data class Request(
        val tokenId: Long,
        val videoId: Long
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true
    )
}
