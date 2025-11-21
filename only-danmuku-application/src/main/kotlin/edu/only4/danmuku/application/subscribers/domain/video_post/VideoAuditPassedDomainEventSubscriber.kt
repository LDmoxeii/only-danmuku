package edu.only4.danmuku.application.subscribers.domain.video_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_message.SendVideoAuditPassedMessageCmd
import edu.only4.danmuku.application.commands.customer_profile.RewardUserForVideoCmd
import edu.only4.danmuku.application.commands.video.TransferVideoToProductionCmd
import edu.only4.danmuku.application.distributed.clients.CleanTempFilesCli
import edu.only4.danmuku.application.queries.video_file_upload_session.GetUploadedTempPathsQry
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
                videoPostId = videoPost.id,
                customerId = videoPost.customerId,
                videoCover = videoPost.videoCover,
                videoName = videoPost.videoName,
                parentCategoryId = videoPost.pCategoryId,
                categoryId = videoPost.categoryId,
                postType = videoPost.postType.code,
                originInfo = videoPost.originInfo,
                tags = videoPost.tags,
                introduction = videoPost.introduction,
                interaction = videoPost.interaction,
                duration = videoPost.duration,
                files = emptyList()
            )
        )
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on2(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        val tempPaths = Mediator.queries.send(
            GetUploadedTempPathsQry.Request(
                customerId = videoPost.customerId,
                videoId = videoPost.id,
            )
        )

        Mediator.requests.send(
            CleanTempFilesCli.Request(
                tempPaths = tempPaths
            )
        )
    }
}
