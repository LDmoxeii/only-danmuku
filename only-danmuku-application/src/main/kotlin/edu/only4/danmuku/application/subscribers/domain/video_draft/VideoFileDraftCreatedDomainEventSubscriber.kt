package edu.only4.danmuku.application.subscribers.domain.video_draft

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_draft.TransferVideoFileCmd
import edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft
import edu.only4.danmuku.domain.aggregates.video_draft.events.VideoFileDraftCreatedDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频文件草稿创建后触发转码
 */
@Service
class VideoFileDraftCreatedDomainEventSubscriber {

    private val logger = LoggerFactory.getLogger(VideoFileDraftCreatedDomainEventSubscriber::class.java)

    @EventListener(VideoFileDraftCreatedDomainEvent::class)
    fun on(event: VideoFileDraftCreatedDomainEvent) {
        val fileDraft = event.entity
        val videoDraft = fileDraft.videoDraft
        if (videoDraft == null) {
            logger.warn("忽略视频文件草稿创建事件，缺少 videoDraft: uploadId={}", fileDraft.uploadId)
            return
        }

        val response = try {
            Mediator.commands.send(
                TransferVideoFileCmd.Request(
                    videoId = videoDraft.id,
                    uploadId = fileDraft.uploadId,
                    customerId = fileDraft.customerId
                )
            )
        } catch (ex: Exception) {
            logger.error(
                "视频文件转码执行异常: uploadId={}",
                fileDraft.uploadId,
                ex
            )
            fileDraft.markTransferFailed()
            updateVideoDraftStatus(videoDraft)
            Mediator.uow.save()
            return
        }

        if (response.success) {
            val duration = response.duration ?: 0
            val fileSize = response.fileSize ?: 0L
            val filePath = response.filePath ?: ""
            fileDraft.markTransferSuccess(duration, fileSize, filePath)
        } else {
            logger.warn(
                "视频文件转码失败，返回错误: uploadId={}, message={}",
                fileDraft.uploadId,
                response.errorMessage
            )
            fileDraft.markTransferFailed()
        }

        updateVideoDraftStatus(videoDraft)
        Mediator.uow.save()
    }

    private fun updateVideoDraftStatus(videoDraft: VideoDraft) {
        when {
            videoDraft.videoFileDrafts.any { it.isTransferFailed() } -> videoDraft.markTranscodeFailed()
            videoDraft.videoFileDrafts.any { it.isTranscoding() } -> videoDraft.markTranscoding()
            else -> {
                videoDraft.markPendingReview()
                val totalDuration = videoDraft.videoFileDrafts.mapNotNull { it.duration }.sum()
                videoDraft.updateDuration(totalDuration)
            }
        }
    }
}
