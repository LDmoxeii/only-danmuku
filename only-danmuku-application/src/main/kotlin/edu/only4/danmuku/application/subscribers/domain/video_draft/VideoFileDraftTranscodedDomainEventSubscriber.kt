package edu.only4.danmuku.application.subscribers.domain.video_draft

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoFileDraftTranscodedDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频文件转码完成后刷新视频草稿转码状态
 */
@Service
class VideoFileDraftTranscodedDomainEventSubscriber {

    private val logger = LoggerFactory.getLogger(VideoFileDraftTranscodedDomainEventSubscriber::class.java)

    @EventListener(VideoFileDraftTranscodedDomainEvent::class)
    fun on(event: VideoFileDraftTranscodedDomainEvent) {
        val videoDraft = event.entity.videoPost
        if (videoDraft == null) {
            logger.warn("忽略视频文件转码事件，缺少 videoDraft: uploadId={}", event.entity.uploadId)
            return
        }
        // 状态刷新已在批量转码命令内完成，这里不再触发独立命令，避免重复
    }
}
