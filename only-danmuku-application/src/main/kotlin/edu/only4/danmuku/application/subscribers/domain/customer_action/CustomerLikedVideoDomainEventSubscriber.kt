package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.UpdateVideoStatisticsCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerLikedVideoDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已点赞视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CustomerLikedVideoDomainEventSubscriber {

    @EventListener(CustomerLikedVideoDomainEvent::class)
    fun on(event: CustomerLikedVideoDomainEvent) {
        Mediator.commands.send(
            UpdateVideoStatisticsCmd.Request(
                videoId = event.entity.videoId.toLong(),
                likeCountDelta = 1
            )
        )
    }
}
