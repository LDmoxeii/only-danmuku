package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingFileEncryptCompletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 单个分P所有清晰度加密完成事件，驱动加密 master 生成
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Service
class VideoPostProcessingFileEncryptCompletedDomainEventSubscriber {

    @EventListener(VideoPostProcessingFileEncryptCompletedDomainEvent::class)
    fun on(event: VideoPostProcessingFileEncryptCompletedDomainEvent) {

    }
}
