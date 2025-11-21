package edu.only4.danmuku.application.subscribers.domain.video_file_post

import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostCreatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 文件分P创建完成事件，驱动转码 CLI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoFilePostCreatedDomainEventSubscriber {

    @EventListener(VideoFilePostCreatedDomainEvent::class)
    fun on(event: VideoFilePostCreatedDomainEvent) {

    }
}
