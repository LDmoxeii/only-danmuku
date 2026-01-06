package edu.only4.danmuku.application.subscribers.domain.video_encrypt

import edu.only4.danmuku.domain.aggregates.video_encrypt.events.VideoPostQualityKeyGeneratedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 单个清晰度密钥已生成事件，驱动单档位加密
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Service
class VideoPostQualityKeyGeneratedDomainEventSubscriber {

    @EventListener(VideoPostQualityKeyGeneratedDomainEvent::class)
    fun on(event: VideoPostQualityKeyGeneratedDomainEvent) {

    }
}
