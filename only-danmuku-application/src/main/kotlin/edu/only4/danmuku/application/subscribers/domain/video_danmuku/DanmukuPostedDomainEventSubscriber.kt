package edu.only4.danmuku.application.subscribers.domain.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.UpdateVideoStatisticsCmd
import edu.only4.danmuku.domain.aggregates.video_danmuku.events.DanmukuPostedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 弹幕已发送
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class DanmukuPostedDomainEventSubscriber {

    @EventListener(DanmukuPostedDomainEvent::class)
    fun on(event: DanmukuPostedDomainEvent) {
        val danmuku = event.entity
        Mediator.commands.send(
            UpdateVideoStatisticsCmd.Request(
                danmuku.videoId,
                danmukuCountDelta = 1
            )
        )
    }
}
