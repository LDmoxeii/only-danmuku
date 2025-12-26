package edu.only4.danmuku.application.subscribers.domain.video_hls_encrypt_key

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_encrypt.UpsertVideoHlsQualityAuthCmd
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.events.VideoHlsEncryptKeyCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.enums.QualityAuthPolicy
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

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
            val publicQualities = setOf("360p", "480p")
            val policy = if (publicQualities.contains(key.quality)) {
                QualityAuthPolicy.PUBLIC.code
            } else {
                QualityAuthPolicy.LOGIN.code
            }

            Mediator.commands.send(
                UpsertVideoHlsQualityAuthCmd.Request(
                    videoFilePostId = key.fileId,
                    quality = key.quality,
                    authPolicy = policy
                )
            )
        }.onFailure { ex ->
            logger.error("UpsertVideoHlsQualityAuth failed for fileId=${key.fileId}", ex)
        }

    }
}
