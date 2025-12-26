package edu.only4.danmuku.application.subscribers.domain.video

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.distributed.clients.video_search.SyncVideoBasicsToEsCli
import edu.only4.danmuku.domain.aggregates.video.events.VideoBasicsSyncedDomainEvent
import org.slf4j.LoggerFactory

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频基础信息已同步
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class VideoBasicsSyncedDomainEventSubscriber {
    private val logger = LoggerFactory.getLogger(VideoBasicsSyncedDomainEventSubscriber::class.java)

    @EventListener(VideoBasicsSyncedDomainEvent::class)
    fun on(event: VideoBasicsSyncedDomainEvent) {
        val video = event.entity
        runCatching {
            Mediator.requests.send(
                SyncVideoBasicsToEsCli.Request(
                    videoId = video.id,
                    videoPostId = video.videoPostId
                )
            )
        }.onFailure { ex ->
            logger.error("SyncVideoBasicsToEs failed for videoId={}", video.id, ex)
        }
    }
}
