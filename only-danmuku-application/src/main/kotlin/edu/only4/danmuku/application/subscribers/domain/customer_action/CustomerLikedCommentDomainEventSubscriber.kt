package edu.only4.danmuku.application.subscribers.domain.customer_action

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_comment.ApplyCustomerLikedCommentCmd
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerLikedCommentDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 用户已点赞评论
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CustomerLikedCommentDomainEventSubscriber {

    @EventListener(CustomerLikedCommentDomainEvent::class)
    fun on(event: CustomerLikedCommentDomainEvent) {
        val action = event.entity
        Mediator.commands.send(
            ApplyCustomerLikedCommentCmd.Request(
                commentId = action.commentId
            )
        )
    }
}
