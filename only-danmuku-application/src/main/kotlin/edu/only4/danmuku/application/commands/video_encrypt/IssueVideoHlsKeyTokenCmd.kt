package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 签发用于播放的短时 token，绑定 fileId+keyId+keyVersion
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object IssueVideoHlsKeyTokenCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                token = TODO("set token"),
                expireAt = TODO("set expireAt"),
                allowedQualities = TODO("set allowedQualities")
            )
        }

    }

    data class Request(
        val fileId: Long,
        val keyId: String,
        val keyVersion: Int,
        val audience: String?,
        val expireSeconds: Int = 600,
        val maxUse: Int = 5,
        val allowedQualities: String?
    ) : RequestParam<Response>

    data class Response(
        val token: String,
        val expireAt: Long,
        val allowedQualities: String?
    )
}
