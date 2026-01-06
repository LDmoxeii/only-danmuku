package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_post.SyncVideoPostProcessStatusCmd
import edu.only4.danmuku.application.queries.video_post_processing.ListVideoPostProcessingFilesForSyncQry
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingCompletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 处理聚合完成事件，驱动稿件状态与稿件文件/分辨率回填
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class VideoPostProcessingCompletedDomainEventSubscriber {

    @EventListener(VideoPostProcessingCompletedDomainEvent::class)
    fun on(event: VideoPostProcessingCompletedDomainEvent) {
        val targetStatus = if (event.failedCount > 0) {
            VideoStatus.TRANSCODE_FAILED
        } else {
            VideoStatus.PENDING_REVIEW
        }

        val files = Mediator.queries.send(
            ListVideoPostProcessingFilesForSyncQry.Request(
                videoPostId = event.videoPostId
            )
        )

        val fileItems = files.map { file ->
            val variants = file.variants.map { variant ->
                SyncVideoPostProcessStatusCmd.VariantItem(
                    quality = variant.quality,
                    width = variant.width,
                    height = variant.height,
                    videoBitrateKbps = variant.videoBitrateKbps,
                    audioBitrateKbps = variant.audioBitrateKbps,
                    bandwidthBps = variant.bandwidthBps,
                    playlistPath = variant.playlistPath,
                    segmentPrefix = variant.segmentPrefix,
                    segmentDuration = variant.segmentDuration
                )
            }
            SyncVideoPostProcessStatusCmd.FileItem(
                fileIndex = file.fileIndex,
                transcodeOutputPrefix = file.transcodeOutputPrefix,
                encryptOutputPrefix = file.encryptOutputPrefix,
                variants = variants,
                duration = file.duration,
                fileSize = file.fileSize,
                encryptMethod = file.encryptMethod,
                keyVersion = file.keyVersion
            )
        }

        Mediator.commands.send(
            SyncVideoPostProcessStatusCmd.Request(
                videoPostId = event.videoPostId,
                targetStatus = targetStatus,
                duration = event.duration,
                failReason = event.lastFailReason,
                fileList = fileItems
            )
        )
    }
}
