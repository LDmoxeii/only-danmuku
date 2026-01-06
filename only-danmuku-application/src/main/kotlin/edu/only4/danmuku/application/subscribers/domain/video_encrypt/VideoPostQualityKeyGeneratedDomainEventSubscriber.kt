package edu.only4.danmuku.application.subscribers.domain.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_post_processing.ApplyVideoPostProcessingVariantEncryptResultCmd
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsVariantWithKeyCli
import edu.only4.danmuku.application.queries.video_post_processing.GetVideoPostProcessingEncryptContextQry
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.events.VideoHlsEncryptKeyCreatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.util.Base64

/**
 * 单档位密钥生成后触发加密
 */
@Service
class VideoPostQualityKeyGeneratedDomainEventSubscriber {

    @EventListener(VideoHlsEncryptKeyCreatedDomainEvent::class)
    fun on(event: VideoHlsEncryptKeyCreatedDomainEvent) {
        val key = event.entity
        if (key.videoPostId <= 0) return
        val quality = key.quality.trim()
        if (quality.isBlank()) return

        val context = Mediator.queries.send(
            GetVideoPostProcessingEncryptContextQry.Request(
                videoPostId = key.videoPostId,
                fileIndex = key.fileIndex
            )
        )
        val sourceDir = context.transcodeOutputPrefix?.takeIf { it.isNotBlank() }
        val outputDir = context.encryptOutputDir?.takeIf { it.isNotBlank() }
        if (sourceDir == null || outputDir == null) {
            Mediator.commands.send(
                ApplyVideoPostProcessingVariantEncryptResultCmd.Request(
                    videoPostId = key.videoPostId,
                    fileIndex = key.fileIndex,
                    quality = quality,
                    success = false,
                    encryptMethod = key.method.name,
                    keyVersion = key.keyVersion,
                    playlistPath = null,
                    segmentPrefix = null,
                    failReason = "加密上下文缺失"
                )
            )
            return
        }

        val keyPlainHex = decodeBase64ToHex(key.keyCiphertext)
        val encrypted = Mediator.requests.send(
            EncryptHlsVariantWithKeyCli.Request(
                sourceDir = sourceDir,
                outputDir = outputDir,
                quality = quality,
                keyPlainHex = keyPlainHex,
                ivHex = key.ivHex,
                keyUriTemplate = key.keyUriTemplate
            )
        )

        Mediator.commands.send(
            ApplyVideoPostProcessingVariantEncryptResultCmd.Request(
                videoPostId = key.videoPostId,
                fileIndex = key.fileIndex,
                quality = quality,
                success = encrypted.success,
                encryptMethod = key.method.name,
                keyVersion = key.keyVersion,
                playlistPath = encrypted.playlistPath,
                segmentPrefix = encrypted.segmentPrefix,
                failReason = encrypted.failReason
            )
        )
    }

    private fun decodeBase64ToHex(base64: String): String {
        val bytes = Base64.getDecoder().decode(base64)
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
