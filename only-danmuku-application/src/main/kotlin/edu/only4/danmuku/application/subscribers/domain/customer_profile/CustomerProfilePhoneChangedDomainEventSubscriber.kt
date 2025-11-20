package edu.only4.danmuku.application.subscribers.domain.customer_profile

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.user.ChangeUserPhoneCmd
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfilePhoneChangedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户手机号在档案中已变更
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class CustomerProfilePhoneChangedDomainEventSubscriber {

    @EventListener(CustomerProfilePhoneChangedDomainEvent::class)
    fun on(event: CustomerProfilePhoneChangedDomainEvent) {
        val customerProfile = event.entity
        Mediator.commands.send(
            ChangeUserPhoneCmd.Request(
                userId = customerProfile.userId,
                phone = customerProfile.phone!!,
            )
        )
    }
}
