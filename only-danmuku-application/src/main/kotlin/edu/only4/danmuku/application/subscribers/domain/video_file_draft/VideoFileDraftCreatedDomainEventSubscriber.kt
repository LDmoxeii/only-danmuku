package edu.only4.danmuku.application.subscribers.domain.video_file_draft

import edu.only4.danmuku.domain.aggregates.video_file_draft.events.VideoFileDraftCreatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频文件草稿已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class VideoFileDraftCreatedDomainEventSubscriber {

    @EventListener(VideoFileDraftCreatedDomainEvent::class)
    fun on(event: VideoFileDraftCreatedDomainEvent) {

    }
}
