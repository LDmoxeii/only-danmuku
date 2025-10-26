package edu.only4.danmuku.application.subscribers.domain.video_draft

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_draft.TransferVideoFileCmd
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoFileDraftCreatedDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频文件草稿创建后触发转码命令
 */
@Service
class VideoFileDraftCreatedDomainEventSubscriber {

    private val logger = LoggerFactory.getLogger(VideoFileDraftCreatedDomainEventSubscriber::class.java)

    @EventListener(VideoFileDraftCreatedDomainEvent::class)
    fun on(event: VideoFileDraftCreatedDomainEvent) {
        val fileDraft = event.entity
        val videoDraft = fileDraft.videoPost
        if (videoDraft == null) {
            logger.warn("忽略视频文件草稿创建事件，缺少 videoDraft: uploadId={}", fileDraft.uploadId)
            return
        }
        try {
            Mediator.commands.send(
                TransferVideoFileCmd.Request(
                    videoPost = videoDraft,
                    fileDraft = fileDraft
                )
            )
        } catch (ex: Exception) {
            logger.error(
                "触发转码命令异常: uploadId={}",
                fileDraft.uploadId,
                ex
            )
        }
    }
}
