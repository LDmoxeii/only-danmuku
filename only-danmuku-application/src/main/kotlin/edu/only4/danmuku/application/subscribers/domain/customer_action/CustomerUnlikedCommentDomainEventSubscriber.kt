package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_comment.ApplyCustomerUnlikedCommentCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUnlikedCommentDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已取消点赞评论事件监听器
 *
 * @author cap4k-ddd-codegen
 * @date 2025/10/23
 */
@Service
class CustomerUnlikedCommentDomainEventSubscriber {

    @EventListener(CustomerUnlikedCommentDomainEvent::class)
    fun on(event: CustomerUnlikedCommentDomainEvent) {
        // 发送取消点赞命令，将评论点赞数减1
        Mediator.commands.send(
            ApplyCustomerUnlikedCommentCmd.Request(
                commentId = event.entity.commentId!!
            )
        )
    }
}
