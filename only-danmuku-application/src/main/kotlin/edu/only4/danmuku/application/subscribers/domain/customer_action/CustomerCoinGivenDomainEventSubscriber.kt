package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.UpdateVideoStatisticsCmd
import edu.only4.danmuku.application.commands.customer_profile.TransferCoinCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerCoinGivenDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已投币视频
 */
@Service
class CustomerCoinGivenDomainEventSubscriber {

    // 1. 硬币转移
    @EventListener(CustomerCoinGivenDomainEvent::class)
    fun on1(event: CustomerCoinGivenDomainEvent) {
        val action = event.entity
        Mediator.commands.send(
            TransferCoinCmd.Request(
                senderUserId = action.customerId.toLong(),
                receiverUserId = action.videoOwnerId.toLong(),
                amount = action.actionCount
            )
        )
    }

    // 2. 更新视频投币统计
    @EventListener(CustomerCoinGivenDomainEvent::class)
    fun on2(event: CustomerCoinGivenDomainEvent) {
        val action = event.entity

        Mediator.commands.send(
            UpdateVideoStatisticsCmd.Request(
                videoId = action.videoId.toLong(),
                coinCountDelta = action.actionCount
            )
        )
    }
}
