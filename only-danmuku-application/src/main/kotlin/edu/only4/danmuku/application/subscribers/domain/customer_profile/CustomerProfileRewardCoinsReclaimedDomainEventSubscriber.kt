package edu.only4.danmuku.application.subscribers.domain.customer_profile

import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileRewardCoinsReclaimedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 客户奖励金币已回收
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class CustomerProfileRewardCoinsReclaimedDomainEventSubscriber {

    @EventListener(CustomerProfileRewardCoinsReclaimedDomainEvent::class)
    fun on(event: CustomerProfileRewardCoinsReclaimedDomainEvent) {

    }
}
