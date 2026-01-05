package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

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
            Mediator.uow.save()

            return Response(
                success = TODO("set success")
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
