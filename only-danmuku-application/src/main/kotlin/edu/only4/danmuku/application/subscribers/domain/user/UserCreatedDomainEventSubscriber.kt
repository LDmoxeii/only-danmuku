package edu.only4.danmuku.application.subscribers.domain.user

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application._share.enums.config.properties.SysSettingProperties
import edu.only4.danmuku.application.commands.customer_profile.CreateCustomerProfileCmd
import edu.only4.danmuku.domain.aggregates.user.events.UserCreatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class UserCreatedDomainEventSubscriber(
    val sysSettingProperties: SysSettingProperties,
) {

    @EventListener(UserCreatedDomainEvent::class)
    fun on(event: UserCreatedDomainEvent) {
        val user = event.entity
        Mediator.commands.send(
            CreateCustomerProfileCmd.Request(
                userid = user.id,
                nickName = user.nickName,
                email = user.email,
                registerCoinCount = sysSettingProperties.registerCoinCount
            )
        )
    }
}
