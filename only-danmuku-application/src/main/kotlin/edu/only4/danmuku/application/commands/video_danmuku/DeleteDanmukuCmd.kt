package edu.only4.danmuku.application.commands.video_danmuku

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_danmuku.SVideoDanmuku

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

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
            // 校验存在
            val danmuku = Mediator.repositories.findFirst(
                SVideoDanmuku.predicateById(request.danmukuId),
                persist = false
            ).getOrNull() ?: throw KnownException("弹幕不存在：${request.danmukuId}")

            // 删除弹幕（软删）
            Mediator.repositories.remove(SVideoDanmuku.predicateById(danmuku.id))

            Mediator.uow.save()

            return Response()
        }
    }

    data class Request(
        /** 弹幕ID */
        val danmukuId: Long
    ) : RequestParam<Response>

    class Response
}
