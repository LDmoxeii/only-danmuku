package edu.only4.danmuku.application.subscribers.domain.video_draft

import edu.only4.danmuku.domain.aggregates.video_post.events.VideoDraftSubmittedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频草稿已提交审核
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class VideoDraftSubmittedDomainEventSubscriber {

    @EventListener(VideoDraftSubmittedDomainEvent::class)
    fun on(event: VideoDraftSubmittedDomainEvent) {

    }
}
