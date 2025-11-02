package edu.only4.danmuku.application.subscribers.domain.customer_profile

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.SpendCoinsForNicknameChangeCmd
import edu.only4.danmuku.application.distributed.clients.RefreshLoginSessionCli
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerNicknameUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户昵称已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CustomerNicknameUpdatedDomainEventSubscriber {

//    @EventListener(CustomerNicknameUpdatedDomainEvent::class)
    fun on(event: CustomerNicknameUpdatedDomainEvent) {
        val profile = event.entity
        Mediator.commands.send(
            SpendCoinsForNicknameChangeCmd.Request(customerId = profile.userId)
        )
        Mediator.requests.send(
            RefreshLoginSessionCli.Request(
                userId = profile.userId,
                nickName = profile.nickName,
                avatar = null
            )
        )
    }
}
