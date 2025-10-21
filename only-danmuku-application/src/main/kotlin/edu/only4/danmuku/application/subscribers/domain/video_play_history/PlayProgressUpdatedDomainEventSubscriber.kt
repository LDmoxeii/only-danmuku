package edu.only4.danmuku.application.subscribers.domain.video_play_history

import edu.only4.danmuku.domain.aggregates.video_play_history.events.PlayProgressUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 播放进度已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class PlayProgressUpdatedDomainEventSubscriber {

    @EventListener(PlayProgressUpdatedDomainEvent::class)
    fun on(event: PlayProgressUpdatedDomainEvent) {

    }
}
