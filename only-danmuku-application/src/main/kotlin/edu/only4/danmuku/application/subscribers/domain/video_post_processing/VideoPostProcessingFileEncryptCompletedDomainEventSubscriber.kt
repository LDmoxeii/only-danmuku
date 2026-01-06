package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_post_processing.ApplyVideoPostProcessingEncryptResultCmd
import edu.only4.danmuku.application.distributed.clients.video_encrypt.GenerateEncryptedMasterByVariantsCli
import edu.only4.danmuku.application.queries.video_post_processing.GetVideoPostProcessingEncryptContextQry
import edu.only4.danmuku.application.queries.video_post_processing.ListVideoPostProcessingVariantsForEncryptMasterQry
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingFileEncryptCompletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 单个分P加密完成后生成 master.m3u8
 */
@Service
class VideoPostProcessingFileEncryptCompletedDomainEventSubscriber {

    @EventListener(VideoPostProcessingFileEncryptCompletedDomainEvent::class)
    fun on(event: VideoPostProcessingFileEncryptCompletedDomainEvent) {
        val context = Mediator.queries.send(
            GetVideoPostProcessingEncryptContextQry.Request(
                videoPostId = event.videoPostId,
                fileIndex = event.fileIndex
            )
        )
        val outputDir = context.encryptOutputDir?.takeIf { it.isNotBlank() }
        if (outputDir == null) {
            Mediator.commands.send(
                ApplyVideoPostProcessingEncryptResultCmd.Request(
                    videoPostId = event.videoPostId,
                    fileIndex = event.fileIndex,
                    success = false,
                    encryptMethod = "HLS_AES_128",
                    keyVersion = 0,
                    failReason = "加密输出目录缺失"
                )
            )
            return
        }

        val variants = Mediator.queries.send(
            ListVideoPostProcessingVariantsForEncryptMasterQry.Request(
                videoPostId = event.videoPostId,
                fileIndex = event.fileIndex
            )
        )
        val variantsJson = JsonUtils.toJsonString(variants) ?: "[]"
        val master = Mediator.requests.send(
            GenerateEncryptedMasterByVariantsCli.Request(
                outputDir = outputDir,
                variantsJson = variantsJson
            )
        )

        Mediator.commands.send(
            ApplyVideoPostProcessingEncryptResultCmd.Request(
                videoPostId = event.videoPostId,
                fileIndex = event.fileIndex,
                success = master.success,
                encryptMethod = "HLS_AES_128",
                keyVersion = 0,
                encryptedMasterPath = master.masterPath,
                failReason = master.failReason
            )
        )
    }
}
