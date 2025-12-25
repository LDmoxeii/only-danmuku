package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 批量轮换所有清晰度 key 并触发重加密
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object RotateVideoHlsKeyBatchCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                newKeyVersion = TODO("set newKeyVersion"),
                failReason = TODO("set failReason")
            )
        }

    }

    data class Request(
        val videoFilePostId: Long,
        val reason: String?
    ) : RequestParam<Response>

    data class Response(
        val newKeyVersion: Int,
        val failReason: String?
    )
}
