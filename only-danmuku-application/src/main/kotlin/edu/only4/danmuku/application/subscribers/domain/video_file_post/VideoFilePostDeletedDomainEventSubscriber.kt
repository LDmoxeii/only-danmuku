package edu.only4.danmuku.application.subscribers.domain.video_file_post

import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostDeletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 分P被删除事件，驱动稿件状态刷新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoFilePostDeletedDomainEventSubscriber {

    @EventListener(VideoFilePostDeletedDomainEvent::class)
    fun on(event: VideoFilePostDeletedDomainEvent) {

    }
}
