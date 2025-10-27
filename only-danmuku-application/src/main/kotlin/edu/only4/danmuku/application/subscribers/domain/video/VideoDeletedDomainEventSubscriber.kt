package edu.only4.danmuku.application.subscribers.domain.video

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.AdjustAuthorCoinAfterDeleteCmd
import edu.only4.danmuku.application.commands.file.DeleteVideoFileResourcesCmd
import edu.only4.danmuku.application.commands.video.RemoveVideoSearchIndexCmd
import edu.only4.danmuku.application.commands.video_danmuku.BatchDeleteDanmukuCmd
import edu.only4.danmuku.application.commands.video_comment.BatchDeleteCommentCmd
import edu.only4.danmuku.application.commands.video_draft.DeleteVideoDraftCmd
import edu.only4.danmuku.domain.aggregates.video.QVideo.video
import edu.only4.danmuku.domain.aggregates.video.events.VideoDeletedDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频被删除领域事件订阅器
 */
@Service
class VideoDeletedDomainEventSubscriber {

    private val logger = LoggerFactory.getLogger(VideoDeletedDomainEventSubscriber::class.java)

    @EventListener(VideoDeletedDomainEvent::class)
    fun removeDraft(event: VideoDeletedDomainEvent) {
        val video = event.entity
        Mediator.commands.send(
            DeleteVideoDraftCmd.Request(
                videoId = video.id,
            )
        )
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun adjustAuthorCoin(event: VideoDeletedDomainEvent) {
        val video = event.entity
        Mediator.commands.send(
            AdjustAuthorCoinAfterDeleteCmd.Request(
                authorId = video.customerId,
            )
        )
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun removeSearchIndex(event: VideoDeletedDomainEvent) {
        val video = event.entity
        Mediator.commands.send(
            RemoveVideoSearchIndexCmd.Request(
                videoId = video.id,
            )
        )
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun cleanupDanmaku(event: VideoDeletedDomainEvent) {
        val video = event.entity
        Mediator.commands.send(
            BatchDeleteDanmukuCmd.Request(
                videoId = video.id,
            )
        )
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun cleanupComments(event: VideoDeletedDomainEvent) {
        val video = event.entity
        Mediator.commands.send(
            BatchDeleteCommentCmd.Request(
                videoId = video.id,
            )
        )
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun deletePhysicalFiles(event: VideoDeletedDomainEvent) {
        val video = event.entity
        Mediator.commands.send(
            DeleteVideoFileResourcesCmd.Request(
                videoId = video.id,
                ownerId = video.customerId,
            )
        )
    }
}

