package edu.only4.danmuku.application.commands.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_danmuku.SVideoDanmuku
import org.springframework.stereotype.Service

/**
 * 批量删除弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object BatchDeleteDanmukuCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.repositories.remove(
                SVideoDanmuku.predicate { it.videoId eq request.videoId }
            )

            Mediator.uow.save()

            return Response()
        }
    }

    data class Request(
        val videoId: Long,
    ) : RequestParam<Response>

    class Response
}
