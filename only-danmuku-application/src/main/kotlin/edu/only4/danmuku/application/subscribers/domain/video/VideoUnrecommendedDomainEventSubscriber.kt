package edu.only4.danmuku.application.subscribers.domain.video

import edu.only4.danmuku.domain.aggregates.video.events.VideoUnrecommendedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频已取消推荐
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class VideoUnrecommendedDomainEventSubscriber {

    @EventListener(VideoUnrecommendedDomainEvent::class)
    fun on(event: VideoUnrecommendedDomainEvent) {

    }
}
