package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 设置分辨率分级授权策略（quality -> auth_policy）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object UpsertVideoHlsQualityAuthCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                updated = TODO("set updated")
            )
        }

    }

    data class Request(
        val fileId: Long,
        val policiesJson: String
    ) : RequestParam<Response>

    data class Response(
        val updated: Boolean = true
    )
}
