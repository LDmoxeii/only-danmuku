package edu.only4.danmuku.application.commands.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.DanmukuDeletePermission
import edu.only4.danmuku.application.validater.DanmukuExists
import edu.only4.danmuku.domain._share.meta.video_danmuku.SVideoDanmuku
import org.springframework.stereotype.Service

/**
 * 删除弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object DeleteDanmukuCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.repositories.remove(
                SVideoDanmuku.predicateById(request.danmukuId)
            )

            Mediator.uow.save()

            return Response()
        }
    }

    @DanmukuDeletePermission
    data class Request(
        /** 弹幕ID */
        @field:DanmukuExists
        val danmukuId: Long,
        /** 操作用户ID；null 表示管理员 */
        val operatorId: Long? = null,
    ) : RequestParam<Response>

    class Response
}
