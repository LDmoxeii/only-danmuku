package edu.only4.danmuku.application.subscribers.domain.video_draft

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.RewardUserForVideoCmd
import edu.only4.danmuku.application.commands.file.CleanTempFilesCmd
import edu.only4.danmuku.application.commands.video_post.TransferVideoToProductionCmd
import edu.only4.danmuku.application.commands.customer_message.SendVideoAuditPassedMessageCmd
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoAuditPassedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频审核通过后的联动逻辑
 */
@Service
class VideoAuditPassedDomainEventSubscriber {

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            RewardUserForVideoCmd.Request(
                customerId = videoPost.customerId,
            )
        )
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on1(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            TransferVideoToProductionCmd.Request(
                videoPost = videoPost
            )
        )
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on2(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            CleanTempFilesCmd.Request(
                customerId = videoPost.customerId,
                videoId = videoPost.id,
            )
        )
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on3(event: VideoAuditPassedDomainEvent) {
        // 发送系统消息给视频作者：审核通过
        val videoPost = event.entity
        Mediator.commands.send(
            SendVideoAuditPassedMessageCmd.Request(
                videoId = videoPost.id,
                operatorId = null
            )
        )
    }
}
