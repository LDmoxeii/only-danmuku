package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingCompletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 处理聚合完成事件，驱动稿件状态与稿件文件/分辨率回填
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class VideoPostProcessingCompletedDomainEventSubscriber {

    @EventListener(VideoPostProcessingCompletedDomainEvent::class)
    fun on(event: VideoPostProcessingCompletedDomainEvent) {

    }
}
