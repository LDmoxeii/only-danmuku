package edu.only4.danmuku.application.subscribers.domain.video

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.AdjustAuthorCoinAfterDeleteCmd
import edu.only4.danmuku.application.commands.file.DeleteVideoFileResourcesCmd
import edu.only4.danmuku.application.commands.video.RemoveVideoSearchIndexCmd
import edu.only4.danmuku.application.commands.video_danmuku.BatchDeleteDanmukuCmd
import edu.only4.danmuku.application.commands.video_comment.BatchDeleteCommentCmd
import edu.only4.danmuku.application.commands.video_draft.DeleteVideoDraftCmd
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
        runCatching {
            Mediator.commands.send(
                DeleteVideoDraftCmd.Request(
                    videoId = video.id,
                )
            )
        }.onFailure { ex ->
            logger.error("删除视频草稿失败: videoId={}", video.id, ex)
        }
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun adjustAuthorCoin(event: VideoDeletedDomainEvent) {
        val video = event.entity
        runCatching {
            Mediator.commands.send(
                AdjustAuthorCoinAfterDeleteCmd.Request(
                    authorId = video.customerId,
                    videoId = video.id,
                )
            )
        }.onFailure { ex ->
            logger.error("调整用户硬币余额失败: videoId={}, authorId={}", video.id, video.customerId, ex)
        }
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun removeSearchIndex(event: VideoDeletedDomainEvent) {
        val video = event.entity
        runCatching {
            Mediator.commands.send(
                RemoveVideoSearchIndexCmd.Request(
                    videoId = video.id,
                )
            )
        }.onFailure { ex ->
            logger.error("删除ES视频文档失败(TODO): videoId={}", video.id, ex)
        }
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun cleanupDanmaku(event: VideoDeletedDomainEvent) {
        val video = event.entity
        runCatching {
            Mediator.commands.send(
                BatchDeleteDanmukuCmd.Request(
                    videoId = video.id,
                )
            )
        }.onFailure { ex ->
            logger.error("删除弹幕数据失败: videoId={}", video.id, ex)
        }
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun cleanupComments(event: VideoDeletedDomainEvent) {
        val video = event.entity
        runCatching {
            Mediator.commands.send(
                BatchDeleteCommentCmd.Request(
                    videoId = video.id,
                )
            )
        }.onFailure { ex ->
            logger.error("删除评论数据失败: videoId={}", video.id, ex)
        }
    }

    @EventListener(VideoDeletedDomainEvent::class)
    fun deletePhysicalFiles(event: VideoDeletedDomainEvent) {
        val video = event.entity
        runCatching {
            Mediator.commands.send(
                DeleteVideoFileResourcesCmd.Request(
                    videoId = video.id,
                    ownerId = video.customerId,
                )
            )
        }.onFailure { ex ->
            logger.error("异步删除硬盘视频文件失败: videoId={}, ownerId={}", video.id, video.customerId, ex)
        }
    }
}

