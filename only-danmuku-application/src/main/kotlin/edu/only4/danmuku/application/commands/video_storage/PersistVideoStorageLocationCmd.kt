package edu.only4.danmuku.application.commands.video_storage

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 更新视频文件的存储前缀
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object PersistVideoStorageLocationCmd {

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
        val videoFilePostId: Long,
        val storagePrefix: String
    ) : RequestParam<Response>

    data class Response(
        val updated: Boolean = true
    )
}
