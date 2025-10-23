package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_action.ApplyCustomerCoinGivenCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerCoinGivenDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已投币视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CustomerCoinGivenDomainEventSubscriber {

    @EventListener(CustomerCoinGivenDomainEvent::class)
    fun on1(event: CustomerCoinGivenDomainEvent) {
        val action = event.entity
        Mediator.commands.send(
            ApplyCustomerCoinGivenCmd.Request(
                senderUserId = action.customerId.toLong(),
                receiverUserId = action.videoOwnerId.toLong(),
                videoId = action.videoId.toLong(),
                coinCount = action.actionCount
            )
        )
    }

    @EventListener(CustomerCoinGivenDomainEvent::class)
    fun on2(event: CustomerCoinGivenDomainEvent) {
        val action = event.entity
        Mediator.commands.send(
            ApplyCustomerCoinGivenCmd.Request(
                senderUserId = action.customerId.toLong(),
                receiverUserId = action.videoOwnerId.toLong(),
                videoId = action.videoId.toLong(),
                coinCount = action.actionCount
            )
        )
    }
}
