package edu.only4.danmuku.application.subscribers.domain.video_file_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostTranscodeResultUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.commands.video_post.RefreshVideoPostTranscodeStatusCmd

/**
 * 单个分P转码结果已回写事件，驱动稿件状态刷新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoFilePostTranscodeResultUpdatedDomainEventSubscriber {

    @EventListener(VideoFilePostTranscodeResultUpdatedDomainEvent::class)
    fun on(event: VideoFilePostTranscodeResultUpdatedDomainEvent) {
        Mediator.commands.send(
            RefreshVideoPostTranscodeStatusCmd.Request(
                videoPostId = event.entity.videoId
            )
        )
    }
}
