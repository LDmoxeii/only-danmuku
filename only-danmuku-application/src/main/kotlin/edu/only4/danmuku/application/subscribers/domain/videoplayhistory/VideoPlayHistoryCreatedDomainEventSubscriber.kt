package edu.only4.danmuku.application.subscribers.domain.videoplayhistory

import edu.only4.danmuku.domain.aggregates.videoplayhistory.events.VideoPlayHistoryCreatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 播放历史记录已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class VideoPlayHistoryCreatedDomainEventSubscriber {

    @EventListener(VideoPlayHistoryCreatedDomainEvent::class)
    fun on(event: VideoPlayHistoryCreatedDomainEvent) {

    }
}
