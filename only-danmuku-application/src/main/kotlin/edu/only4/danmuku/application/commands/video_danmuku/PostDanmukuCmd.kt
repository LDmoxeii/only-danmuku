package edu.only4.danmuku.application.commands.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.DanmukuInteractionAllowed
import edu.only4.danmuku.application.validator.DanmukuTextFormat
import edu.only4.danmuku.application.validator.VideoExists
import edu.only4.danmuku.domain.aggregates.video_danmuku.factory.VideoDanmukuFactory
import org.springframework.stereotype.Service

/**
 * 发送弹幕
 */
object PostDanmukuCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
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
        }
    }

    @DanmukuTextFormat(modeField = "mode", timeField = "time")
    data class Request(
        @field:VideoExists
        @field:DanmukuInteractionAllowed
        val videoId: Long,
        val fileId: Long,
        val customerId: Long,
        val text: String,
        val mode: Int,
        val color: String,
        val time: Int,
    ) : RequestParam<Unit>
}
