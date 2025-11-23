package edu.only4.danmuku.application.subscribers.domain.video_comment

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.ApplyVideoCommentCountDeltaCmd
import edu.only4.danmuku.application.commands.customer_message.SendCommentMessageCmd
import edu.only4.danmuku.application.commands.customer_message.SendReplyMessageCmd
import edu.only4.danmuku.application.queries.video_comment.GetCommentByIdQry
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentPostedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
 

/**
 * 评论已发表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class CommentPostedDomainEventSubscriber {

    @EventListener(CommentPostedDomainEvent::class)
    fun on(event: CommentPostedDomainEvent) {
        val comment = event.entity
        if (comment.parentId == 0L) {
            Mediator.commands.send(
                ApplyVideoCommentCountDeltaCmd.Request(
                    videoId = comment.videoId,
                    delta = 1
                )
            )
        }
    }

    @EventListener(CommentPostedDomainEvent::class)
    fun on1(event: CommentPostedDomainEvent) {
        val c = event.entity
        if (c.parentId == 0L) {
            // 顶级评论 → 给视频作者发送评论消息
            Mediator.commands.send(
                SendCommentMessageCmd.Request(
                    videoId = c.videoId,
                    sendUserId = c.customerId,
                    content = c.content,
                )
            )
        }
    }

    @EventListener(CommentPostedDomainEvent::class)
    fun on2(event: CommentPostedDomainEvent) {
        val c = event.entity
        if (c.parentId == 0L) return
        // 查询父评论（不依赖仓储）— 获取被回复评论的内容和作者
        val parent = Mediator.queries.send(GetCommentByIdQry.Request(commentId = c.parentId))

        Mediator.commands.send(
            SendReplyMessageCmd.Request(
                videoId = c.videoId,
                sendUserId = c.customerId,
                content = c.content,
                replyCommentId = c.parentId,
                replyCommentContent = parent.content,
            )
        )
    }

}
