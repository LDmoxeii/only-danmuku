package edu.only4.danmuku.application.subscribers.domain.video_hls_encrypt_key

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_encrypt.PersistVideoEncryptResultCmd
import edu.only4.danmuku.application.commands.video_encrypt.UpsertVideoHlsQualityAuthCmd
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithKeyCli
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.events.VideoHlsEncryptKeyCreatedDomainEvent
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.enums.QualityAuthPolicy
import com.only.engine.json.misc.JsonUtils
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.util.Base64
import kotlin.jvm.optionals.getOrNull

/**
 * 视频 HLS 加密密钥创建后，触发 HLS 加密编排
 */
@Service
class VideoHlsEncryptKeyCreatedDomainEventSubscriber {
    private val logger = LoggerFactory.getLogger(VideoHlsEncryptKeyCreatedDomainEventSubscriber::class.java)

    @EventListener(VideoHlsEncryptKeyCreatedDomainEvent::class)
    fun on(event: VideoHlsEncryptKeyCreatedDomainEvent) {
        val key = event.entity
        runCatching {
            val path = Mediator.queries.send(
                GetVideoFilePostPathQry.Request(filePostId = key.fileId)
            ).filePath ?: throw IllegalStateException("filePath 为空")

            val encResp = Mediator.requests.send(
                EncryptHlsWithKeyCli.Request(
                    videoFilePostId = key.fileId,
                    sourceDir = path,
                    outputDir = "$path/enc",
                    keyId = key.keyId,
                    quality = key.quality,
                    keyPlainHex = decodeBase64ToHex(key.keyCiphertext),
                    ivHex = key.ivHex,
                    segmentExt = ".ts.enc"
                )
            )

            Mediator.commands.send(
                PersistVideoEncryptResultCmd.Request(
                    videoFilePostId = key.fileId,
                    success = encResp.success,
                    encryptMethod = key.method.code,
                    keyId = key.keyId,
                    keyVersion = key.keyVersion,
                    keyQuality = key.quality,
                    encryptedMasterPath = encResp.encryptedMasterPath,
                    encryptedVariants = encResp.encryptedVariants,
                    failReason = encResp.failReason
                )
            )
        }.onFailure { ex ->
            logger.error("EncryptHlsWithKey failed for fileId=${key.fileId}, keyId=${key.keyId}", ex)
            Mediator.commands.send(
                PersistVideoEncryptResultCmd.Request(
                    videoFilePostId = key.fileId,
                    success = false,
                    encryptMethod = key.method.code,
                    keyId = key.keyId,
                    keyVersion = key.keyVersion,
                    keyQuality = key.quality,
                    encryptedMasterPath = null,
                    encryptedVariants = null,
                    failReason = ex.message
                )
            )
        }
    }

    @EventListener(VideoHlsEncryptKeyCreatedDomainEvent::class)
    fun upsertVideoHlsQualityAuth(event: VideoHlsEncryptKeyCreatedDomainEvent) {
        val key = event.entity
        runCatching {
            val filePost = Mediator.repositories.findOne(
                SVideoFilePost.predicateById(key.fileId)
            ).getOrNull() ?: throw IllegalStateException("分P不存在: ${key.fileId}")

            val qualities = filePost.videoHlsAbrVariants
                .mapNotNull { it.quality.takeIf(String::isNotBlank) }
                .ifEmpty { listOfNotNull(key.quality) }
                .ifEmpty { listOf("360p", "480p", "720p", "1080p") }

            val publicQualities = setOf("360p", "480p")
            val policies = qualities.distinct().map { quality ->
                val policy = if (publicQualities.contains(quality)) {
                    QualityAuthPolicy.PUBLIC.code
                } else {
                    QualityAuthPolicy.LOGIN.code
                }
                UpsertVideoHlsQualityAuthCmd.PolicyPayload(
                    quality = quality,
                    authPolicy = policy
                )
            }
            val policiesJson = JsonUtils.toJsonString(policies) ?: "[]"

            Mediator.commands.send(
                UpsertVideoHlsQualityAuthCmd.Request(
                    videoFilePostId = key.fileId,
                    policiesJson = policiesJson
                )
            )
        }.onFailure { ex ->
            logger.error("UpsertVideoHlsQualityAuth failed for fileId=${key.fileId}", ex)
        }

    }

    private fun decodeBase64ToHex(cipherBase64: String): String {
        val bytes = Base64.getDecoder().decode(cipherBase64)
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
