package edu.only4.danmuku.application.subscribers.domain.video_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_message.SendVideoAuditPassedMessageCmd
import edu.only4.danmuku.application.commands.customer_profile.RewardUserForVideoCmd
import edu.only4.danmuku.application.commands.video.TransferVideoToProductionCmd
import edu.only4.danmuku.application.commands.video_post.RecordVideoAuditTraceCmd
import edu.only4.danmuku.application.distributed.clients.CleanTempFilesCli
import edu.only4.danmuku.application.queries.video_file_upload_session.GetUploadedTempPathsQry
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
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
                files = videoPost.videoFilePosts
                    .filter { it.isTransferSuccess() }
                    .sortedBy { it.fileIndex }
                    .map {
                        TransferVideoToProductionCmd.FileItem(
                            videoFilePostId = it.id,
                            customerId = it.customerId,
                            fileName = it.fileName,
                            fileIndex = it.fileIndex,
                            fileSize = it.fileSize,
                            filePath = it.filePath,
                            duration = it.duration,
                        )
                    }
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

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun onAuditTrace(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        val reviewerId = event.reviewerId
        val reviewerType = event.reviewerType
        Mediator.commands.send(
            RecordVideoAuditTraceCmd.Request(
                videoPostId = videoPost.id,
                auditStatus = AuditStatus.PASSED,
                reviewerId = reviewerId,
                reviewerType = reviewerType,
                reason = null,
                occurTime = System.currentTimeMillis() / 1000L
            )
        )
    }
}
