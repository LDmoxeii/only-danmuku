package edu.only4.danmuku.application.commands.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.DanmukuInteractionAllowed
import edu.only4.danmuku.application.validater.DanmukuTextFormat
import edu.only4.danmuku.application.validater.VideoExists
import edu.only4.danmuku.domain.aggregates.video_danmuku.factory.VideoDanmukuFactory

import org.springframework.stereotype.Service

/**
 * 发送弹幕
 */
object PostDanmukuCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val now = System.currentTimeMillis() / 1000

            val payload = VideoDanmukuFactory.Payload(
                videoId = request.videoId,
                fileId = request.fileId,
                customerId = request.customerId,
                postTime = now,
                text = request.text,
                mode = request.mode != 0,
                color = request.color,
                time = request.time
            )

            Mediator.factories.create(payload)

            Mediator.uow.save()
            return Response()
        }
    }

    @DanmukuTextFormat(modeField = "mode", timeField = "time")
    data class Request(
        /** 视频ID */
        @field:VideoExists
        @field:DanmukuInteractionAllowed
        val videoId: Long,
        /** 文件ID */
        val fileId: Long,
        /** 用户ID */
        val customerId: Long,
        /** 弹幕内容 */
        val text: String,
        /** 弹幕模式 */
        val mode: Int,
        /** 弹幕颜色 */
        val color: String,
        /** 弹幕时间(秒) */
        val time: Int,
    ) : RequestParam<Response>

    class Response
}
