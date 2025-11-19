package edu.only4.danmuku.application.subscribers.domain.video

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_comment.BatchDeleteCommentCmd
import edu.only4.danmuku.application.commands.video_danmuku.BatchDeleteDanmukuCmd
import edu.only4.danmuku.application.distributed.clients.RemoveVideoSearchIndexCli
import edu.only4.danmuku.domain.aggregates.video.events.VideoDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频已删除
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
 */
@Service
class VideoDeletedDomainEventSubscriber {

    @EventListener(VideoDeletedDomainEvent::class)
    fun removeSearchIndex(event: VideoDeletedDomainEvent) {
        val video = event.entity
        Mediator.requests.send(
            RemoveVideoSearchIndexCli.Request(
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
}
