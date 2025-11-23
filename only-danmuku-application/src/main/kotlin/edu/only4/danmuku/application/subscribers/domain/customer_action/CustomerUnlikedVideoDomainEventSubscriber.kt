package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.ApplyVideoLikeCountDeltaCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUnlikedVideoDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已取消点赞视频事件监听器
 *
 * @author cap4k-ddd-codegen
 * @date 2025/10/23
 */
@Service
class CustomerUnlikedVideoDomainEventSubscriber {

    @EventListener(CustomerUnlikedVideoDomainEvent::class)
    fun on(event: CustomerUnlikedVideoDomainEvent) {
        Mediator.commands.send(
            ApplyVideoLikeCountDeltaCmd.Request(
                videoId = event.entity.videoId,
                delta = -1
            )
        )
    }
}
