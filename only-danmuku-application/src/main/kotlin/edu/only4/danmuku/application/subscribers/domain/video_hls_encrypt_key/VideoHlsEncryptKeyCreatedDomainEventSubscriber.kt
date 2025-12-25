package edu.only4.danmuku.application.subscribers.domain.video_hls_encrypt_key

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_encrypt.UpsertVideoHlsQualityAuthCmd
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.events.VideoHlsEncryptKeyCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.enums.QualityAuthPolicy
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 视频 HLS 加密密钥创建后，触发 HLS 加密编排
 */
@Service
class VideoHlsEncryptKeyCreatedDomainEventSubscriber {
    private val logger = LoggerFactory.getLogger(VideoHlsEncryptKeyCreatedDomainEventSubscriber::class.java)

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
}
