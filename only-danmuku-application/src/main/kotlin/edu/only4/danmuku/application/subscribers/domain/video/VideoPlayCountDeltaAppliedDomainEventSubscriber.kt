package edu.only4.danmuku.application.subscribers.domain.video

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.statistics.UpdateStatisticsInfoCmd
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import edu.only4.danmuku.domain.aggregates.video.events.VideoPlayCountDeltaAppliedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频播放数变更
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/23
 */
@Service
class VideoPlayCountDeltaAppliedDomainEventSubscriber {

    @EventListener(VideoPlayCountDeltaAppliedDomainEvent::class)
    fun on(event: VideoPlayCountDeltaAppliedDomainEvent) {
        if (event.delta == 0) return
        val video = event.entity

        Mediator.commands.send(
            UpdateStatisticsInfoCmd.Request(
                customerId = video.customerId,
                dataType = StatisticsDataType.PLAY,
                countDelta = event.delta
            )
        )
    }
}
