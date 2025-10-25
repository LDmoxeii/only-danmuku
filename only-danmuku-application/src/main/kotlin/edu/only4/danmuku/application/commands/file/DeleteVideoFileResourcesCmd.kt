package edu.only4.danmuku.application.commands.file

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import org.springframework.stereotype.Service

/**
 * 从硬盘物理删除视频文件资源
 */
object DeleteVideoFileResourcesCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // TODO: 实现硬盘文件删除逻辑 (批删除视频实际存放的文件夹、缩略图目录)
            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val videoId: Long,
        val ownerId: Long,
    ) : RequestParam<Response>

    class Response
}

