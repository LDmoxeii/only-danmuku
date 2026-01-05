package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingTranscodeCompletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 单个分P转码完成事件，携带 outputPrefix/encOutputDir/variantsJson，驱动加密编排
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class VideoPostProcessingTranscodeCompletedDomainEventSubscriber {

    @EventListener(VideoPostProcessingTranscodeCompletedDomainEvent::class)
    fun on(event: VideoPostProcessingTranscodeCompletedDomainEvent) {

    }
}
