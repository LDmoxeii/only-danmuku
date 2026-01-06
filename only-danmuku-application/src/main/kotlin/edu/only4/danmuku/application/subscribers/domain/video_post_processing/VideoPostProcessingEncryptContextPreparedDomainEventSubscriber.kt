package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_encrypt.GenerateVideoPostQualityKeyCmd
import edu.only4.danmuku.application.commands.video_post_processing.ApplyVideoPostProcessingEncryptResultCmd
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingEncryptContextPreparedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 加密上下文准备完成后生成单档位密钥
 */
@Service
class VideoPostProcessingEncryptContextPreparedDomainEventSubscriber {

    @EventListener(VideoPostProcessingEncryptContextPreparedDomainEvent::class)
    fun on(event: VideoPostProcessingEncryptContextPreparedDomainEvent) {
        val qualities = parseQualities(event.variantsJson)
        val outputPrefix = event.transcodeOutputPrefix?.takeIf { it.isNotBlank() }
        val encOutputDir = event.encryptOutputDir?.takeIf { it.isNotBlank() }
        if (outputPrefix == null || encOutputDir == null || qualities.isEmpty()) {
            Mediator.commands.send(
                ApplyVideoPostProcessingEncryptResultCmd.Request(
                    videoPostId = event.videoPostId,
                    fileIndex = event.fileIndex,
                    success = false,
                    encryptMethod = "HLS_AES_128",
                    keyVersion = event.keyVersion,
                    failReason = "加密入参缺失"
                )
            )
            return
        }

        qualities.forEach { quality ->
            Mediator.commands.send(
                GenerateVideoPostQualityKeyCmd.Request(
                    videoPostId = event.videoPostId,
                    fileIndex = event.fileIndex,
                    quality = quality,
                    keyVersion = event.keyVersion,
                    method = "HLS_AES_128",
                    keyBytes = 16
                )
            )
        }
    }

    private fun parseQualities(variantsJson: String?): List<String> {
        if (variantsJson.isNullOrBlank()) return emptyList()
        val variants = JsonUtils.parseArray(variantsJson, VariantPayload::class.java)
        return variants.mapNotNull { it.quality.trim().takeIf(String::isNotBlank) }.distinct()
    }

    data class VariantPayload(
        val quality: String = "",
    )
}
