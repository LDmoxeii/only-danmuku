package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 审核通过后补充 videoId（密钥转正绑定）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object BindVideoHlsEncryptKeyToVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val encryptKey = Mediator.repositories.findOne(
                SVideoHlsEncryptKey.predicateById(request.encryptKeyId)
            ).getOrNull() ?: throw KnownException("密钥不存在: ${request.encryptKeyId}")

            encryptKey.bindVideoId(request.videoId)
            Mediator.uow.save()

            return Response(
                success = true
            )
        }

    }

    data class Request(
        val encryptKeyId: Long,
        val videoId: Long
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true
    )
}
