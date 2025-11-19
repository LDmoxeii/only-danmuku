package edu.only4.danmuku.adapter.application.distributed.clients

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.RemoveVideoSearchIndexCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 删除视频搜索索引
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
@Service
class RemoveVideoSearchIndexCliHandler : RequestHandler<RemoveVideoSearchIndexCli.Request, Unit>{

    private val logger = LoggerFactory.getLogger(RemoveVideoSearchIndexCliHandler::class.java)
    override fun exec(request: RemoveVideoSearchIndexCli.Request) {
        // TODO: 调用 ES 中删除逻辑
        logger.info("RemoveVideoSearchIndexCmd 未实现, 待接入 ES: videoId={}", request.videoId)
        Mediator.uow.save()
    }
}

