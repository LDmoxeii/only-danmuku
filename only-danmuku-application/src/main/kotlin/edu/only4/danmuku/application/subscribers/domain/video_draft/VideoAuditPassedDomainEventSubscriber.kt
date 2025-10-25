package edu.only4.danmuku.application.subscribers.domain.video_draft

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.RewardUserForVideoCmd
import edu.only4.danmuku.application.commands.file.CleanTempFilesCmd
import edu.only4.danmuku.application.commands.video_draft.TransferVideoToProductionCmd
import edu.only4.danmuku.domain.aggregates.video_draft.events.VideoAuditPassedDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频审核通过后的联动逻辑
 */
@Service
class VideoAuditPassedDomainEventSubscriber {

    private val logger = LoggerFactory.getLogger(VideoAuditPassedDomainEventSubscriber::class.java)

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on(event: VideoAuditPassedDomainEvent) {
        val videoDraft = event.entity
        try {
            Mediator.commands.send(
                RewardUserForVideoCmd.Request(
                    customerId = videoDraft.customerId,
                    videoId = videoDraft.id,
                )
            )
        } catch (ex: Exception) {
            logger.error("首次发布奖励命令执行失败: videoId={}", videoDraft.id, ex)
        }
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on1(event: VideoAuditPassedDomainEvent) {
        val videoDraft = event.entity
        try {
            Mediator.commands.send(
                TransferVideoToProductionCmd.Request(
                    videoDraft = videoDraft
                )
            )
        } catch (ex: Exception) {
            logger.error("转移视频至正式库失败: videoId={}", videoDraft.id, ex)
        }
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on2(event: VideoAuditPassedDomainEvent) {
        val videoDraft = event.entity
        try {
            Mediator.commands.send(
                CleanTempFilesCmd.Request(
                    customerId = videoDraft.customerId,
                    videoId = videoDraft.id,
                )
            )
        } catch (ex: Exception) {
            logger.error("清理临时文件失败: videoId={}", videoDraft.id, ex)
        }
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on3(event: VideoAuditPassedDomainEvent) {
        // TODO： SyncToElasticsearchCmd ? (同步到ES) 展示未实现
    }
}
