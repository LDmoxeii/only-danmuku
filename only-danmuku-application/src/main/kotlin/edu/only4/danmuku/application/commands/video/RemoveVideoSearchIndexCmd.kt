package edu.only4.danmuku.application.commands.video

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 从 ES 中删除视频搜索文档信息 (未实现, 标记 TODO)
 */
object RemoveVideoSearchIndexCmd {

    private val logger = LoggerFactory.getLogger(RemoveVideoSearchIndexCmd::class.java)

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // TODO: 调用 ES 中删除逻辑
            logger.info("RemoveVideoSearchIndexCmd 未实现, 待接入 ES: videoId={}", request.videoId)
            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val videoId: Long,
    ) : RequestParam<Response>

    class Response
}

