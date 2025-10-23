package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.UpdateVideoStatisticsCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUncollectedVideoDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已取消收藏视频事件监听器
 *
 * @author cap4k-ddd-codegen
 * @date 2025/10/23
 */
@Service
class CustomerUncollectedVideoDomainEventSubscriber {

    @EventListener(CustomerUncollectedVideoDomainEvent::class)
    fun on(event: CustomerUncollectedVideoDomainEvent) {
        // 发送更新视频统计数据命令，将收藏数减1
        Mediator.commands.send(
            UpdateVideoStatisticsCmd.Request(
                videoId = event.entity.videoId.toLong(),
                collectCountDelta = -1
            )
        )
    }
}
