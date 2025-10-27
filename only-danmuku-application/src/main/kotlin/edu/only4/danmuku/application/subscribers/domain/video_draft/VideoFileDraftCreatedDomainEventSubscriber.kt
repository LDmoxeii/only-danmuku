package edu.only4.danmuku.application.subscribers.domain.video_draft

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_draft.TransferVideoFileCmd
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoFileDraftCreatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频文件草稿创建后触发转码命令
 */
@Service
class VideoFileDraftCreatedDomainEventSubscriber {

    @EventListener(VideoFileDraftCreatedDomainEvent::class)
    fun on(event: VideoFileDraftCreatedDomainEvent) {
        val fileDraft = event.entity
        val videoDraft = fileDraft.videoPost!!

        Mediator.commands.send(
            TransferVideoFileCmd.Request(
                videoPost = videoDraft,
                fileDraft = fileDraft
            )
        )
    }
}
