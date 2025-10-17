package edu.only4.danmuku.application.commands.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 发送弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object PostDanmukuCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
            )
        }

    }

    data class Request(
        /** 视频ID */
        val videoId: Long,
        /** 文件ID */
        val fileId: Long,
        /** 弹幕内容 */
        val text: String,
        /** 弹幕模式 */
        val mode: Int,
        /** 弹幕颜色 */
        val color: String,
        /** 弹幕时间(秒) */
        val time: Int
    ) : RequestParam<Response>

    class Response()
}
