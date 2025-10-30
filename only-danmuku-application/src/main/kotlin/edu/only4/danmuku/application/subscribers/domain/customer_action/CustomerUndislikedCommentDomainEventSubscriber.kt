package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_comment.ApplyCustomerUndislikedCommentCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUndislikedCommentDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已取消点踩评论事件监听器
 *
 * @author cap4k-ddd-codegen
 * @date 2025/10/23
 */
@Service
class CustomerUndislikedCommentDomainEventSubscriber {

    @EventListener(CustomerUndislikedCommentDomainEvent::class)
    fun on(event: CustomerUndislikedCommentDomainEvent) {
        // 发送取消点踩命令，将评论点踩数减1
        Mediator.commands.send(
            ApplyCustomerUndislikedCommentCmd.Request(
                commentId = event.entity.commentId!!
            )
        )
    }
}
