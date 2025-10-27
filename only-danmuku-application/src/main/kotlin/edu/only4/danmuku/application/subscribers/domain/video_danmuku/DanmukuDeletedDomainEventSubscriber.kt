package edu.only4.danmuku.application.subscribers.domain.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.UpdateVideoStatisticsCmd
import edu.only4.danmuku.domain.aggregates.video_danmuku.events.DanmukuDeletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 弹幕已删除
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class DanmukuDeletedDomainEventSubscriber {

    @EventListener(DanmukuDeletedDomainEvent::class)
    fun on(event: DanmukuDeletedDomainEvent) {
        val danmuku = event.entity
        Mediator.commands.send(
            UpdateVideoStatisticsCmd.Request(
                danmuku.videoId,
                danmukuCountDelta = -1
            )
        )
    }
}
