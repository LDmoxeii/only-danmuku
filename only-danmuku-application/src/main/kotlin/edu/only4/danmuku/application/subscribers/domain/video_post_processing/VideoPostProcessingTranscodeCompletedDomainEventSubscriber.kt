package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_encrypt.GenerateVideoPostQualityKeysCmd
import edu.only4.danmuku.application.commands.video_post_processing.ApplyVideoPostProcessingEncryptResultCmd
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithQualityKeysCli
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingTranscodeCompletedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 单个分P转码完成事件，携带 outputPrefix/encOutputDir/variantsJson，驱动加密编排
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class VideoPostProcessingTranscodeCompletedDomainEventSubscriber {

    @EventListener(VideoPostProcessingTranscodeCompletedDomainEvent::class)
    fun on(event: VideoPostProcessingTranscodeCompletedDomainEvent) {
        val outputPrefix = event.outputPrefix?.takeIf { it.isNotBlank() }
        val encOutputDir = event.encOutputDir?.takeIf { it.isNotBlank() }
        val qualities = parseQualities(event.variantsJson)
        if (outputPrefix == null || encOutputDir == null || qualities.isEmpty()) {
            Mediator.commands.send(
                ApplyVideoPostProcessingEncryptResultCmd.Request(
                    videoPostId = event.videoPostId,
                    fileIndex = event.fileIndex,
                    success = false,
                    encryptMethod = "HLS_AES_128",
                    keyVersion = 0,
                    encryptedPrefix = null,
                    encryptedMasterPath = null,
                    encryptedVariants = null,
                    failReason = "加密入参缺失"
                )
            )
            return
        }

        val generated = Mediator.commands.send(
            GenerateVideoPostQualityKeysCmd.Request(
                videoPostId = event.videoPostId,
                fileIndex = event.fileIndex,
                qualities = qualities,
                method = "HLS_AES_128",
                keyBytes = 16
            )
        )

        val encrypted = Mediator.requests.send(
            EncryptHlsWithQualityKeysCli.Request(
                sourceDir = outputPrefix,
                outputDir = encOutputDir,
                keysJson = generated.keysJson
            )
        )

        Mediator.commands.send(
            ApplyVideoPostProcessingEncryptResultCmd.Request(
                videoPostId = event.videoPostId,
                fileIndex = event.fileIndex,
                success = encrypted.success,
                encryptMethod = "HLS_AES_128",
                keyVersion = generated.keyVersion,
                encryptedPrefix = resolveOutputPrefix(encrypted.encryptedMasterPath),
                encryptedMasterPath = encrypted.encryptedMasterPath,
                encryptedVariants = encrypted.encryptedVariants,
                failReason = encrypted.failReason
            )
        )
    }

    private fun parseQualities(variantsJson: String?): List<String> {
        if (variantsJson.isNullOrBlank()) return emptyList()
        val variants = JsonUtils.parseArray(variantsJson, VariantPayload::class.java)
        return variants.mapNotNull { it.quality.trim().takeIf(String::isNotBlank) }.distinct()
    }

    private fun resolveOutputPrefix(masterPath: String?): String? {
        if (masterPath.isNullOrBlank()) return null
        val trimmed = masterPath.trim()
        return if (trimmed.endsWith("/master.m3u8")) {
            trimmed.removeSuffix("/master.m3u8")
        } else {
            trimmed.substringBeforeLast("/", trimmed)
        }.trimEnd('/')
    }

    data class VariantPayload(
        val quality: String = "",
    )
}
