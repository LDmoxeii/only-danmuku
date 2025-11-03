package edu.only4.danmuku.application.subscribers.domain.video_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_post.TranscodeAllTranscodingFilesCmd
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoPostTranscodingRequiredDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 当视频稿件需要转码时（例如新增了分P），触发批量转码命令。
 */
@Service
class VideoPostTranscodingRequiredDomainEventSubscriber {

    @EventListener(VideoPostTranscodingRequiredDomainEvent::class)
    fun on(event: VideoPostTranscodingRequiredDomainEvent) {
        val videoPost = event.entity

        Mediator.cmd.send(
            TranscodeAllTranscodingFilesCmd.Request(
                videoPostId = videoPost.id
            )
        )
    }
}

