package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.UpdateVideoStatisticsCmd
import edu.only4.danmuku.application.commands.customer_message.SendCollectMessageCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerCollectedVideoDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已收藏视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CustomerCollectedVideoDomainEventSubscriber {

    @EventListener(CustomerCollectedVideoDomainEvent::class)
    fun on(event: CustomerCollectedVideoDomainEvent) {
        // 发送更新视频统计数据命令，将收藏数加1
        Mediator.commands.send(
            UpdateVideoStatisticsCmd.Request(
                videoId = event.entity.videoId.toLong(),
                collectCountDelta = 1
            )
        )
    }

    @EventListener(CustomerCollectedVideoDomainEvent::class)
    fun onSendMessage(event: CustomerCollectedVideoDomainEvent) {
        val action = event.entity
        Mediator.commands.send(
            SendCollectMessageCmd.Request(
                videoId = action.videoId.toLong(),
                sendUserId = action.customerId.toLong()
            )
        )
    }
}
