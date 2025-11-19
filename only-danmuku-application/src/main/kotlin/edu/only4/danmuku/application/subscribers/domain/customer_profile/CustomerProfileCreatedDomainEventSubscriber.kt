package edu.only4.danmuku.application.subscribers.domain.customer_profile

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.user.RelationProfileCmd
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileCreatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户档案已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CustomerProfileCreatedDomainEventSubscriber {

    @EventListener(CustomerProfileCreatedDomainEvent::class)
    fun on1(event: CustomerProfileCreatedDomainEvent) {
        val customerProfile = event.entity
        Mediator.commands.send(
            RelationProfileCmd.Request(
                userId = customerProfile.userId,
                profileId = customerProfile.id
            )
        )
    }
}
