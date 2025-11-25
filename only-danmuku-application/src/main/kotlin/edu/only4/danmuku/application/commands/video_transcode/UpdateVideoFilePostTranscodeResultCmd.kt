package edu.only4.danmuku.application.commands.video_transcode

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 根据多路转码结果更新 VideoFilePost 状态并写入 ABR 元数据与档位
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object UpdateVideoFilePostTranscodeResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                transferResult = TODO("set transferResult")
            )
        }

    }

    data class Request(
        val videoFilePostId: Long,
        val success: Boolean,
        val sourceWidth: Int?,
        val sourceHeight: Int?,
        val sourceBitrateKbps: Int?,
        val variants: String,
        val duration: Int?,
        val fileSize: Long?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val transferResult: Int
    )
}
