package edu.only4.danmuku.application.subscribers.domain.video

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_encrypt.BindVideoHlsEncryptKeyToVideoCmd
import edu.only4.danmuku.application.commands.video_encrypt.BindVideoHlsKeyTokenToVideoCmd
import edu.only4.danmuku.application.commands.video_quality_policy.SetVideoQualityPolicyCmd
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsEncryptKeysByPostFileQry
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsKeyTokensByPostFileQry
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoAuditPassedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_quality_policy.enums.QualityAuthPolicy
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 视频基础信息已同步
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class VideoBasicsSyncedDomainEventSubscriber {

        @EventListener(VideoAuditPassedDomainEvent::class)
    fun on(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        val video = Mediator.repositories.findFirst(
            SVideo.predicate { schema ->
                schema.videoPostId.eq(videoPost.id)
            }
        ).getOrNull() ?: return

        videoPost.videoFilePosts.forEach { file ->
            val keyIds = Mediator.queries.send(
                ListVideoHlsEncryptKeysByPostFileQry.Request(
                    videoPostId = videoPost.id,
                    fileIndex = file.fileIndex
                )
            )
            keyIds.forEach { key ->
                Mediator.commands.send(
                    BindVideoHlsEncryptKeyToVideoCmd.Request(
                        encryptKeyId = key.encryptKeyId,
                        videoId = video.id
                    )
                )
            }

            val tokenIds = Mediator.queries.send(
                ListVideoHlsKeyTokensByPostFileQry.Request(
                    videoPostId = videoPost.id,
                    fileIndex = file.fileIndex
                )
            )
            tokenIds.forEach { token ->
                Mediator.commands.send(
                    BindVideoHlsKeyTokenToVideoCmd.Request(
                        tokenId = token.tokenId,
                        videoId = video.id
                    )
                )
            }

            file.videoFilePostVariants
                .map { variant -> variant.quality.trim() }
                .filter(String::isNotBlank)
                .distinct()
                .forEach { quality ->
                    val authPolicy = if (isPublicQuality(quality)) {
                        QualityAuthPolicy.PUBLIC
                    } else {
                        QualityAuthPolicy.LOGIN
                    }
                    Mediator.commands.send(
                        SetVideoQualityPolicyCmd.Request(
                            videoId = video.id,
                            fileIndex = file.fileIndex,
                            quality = quality,
                            authPolicy = authPolicy,
                            remark = null
                        )
                    )
                }
        }
    }

    private fun isPublicQuality(quality: String): Boolean {
        return when (quality.trim().lowercase()) {
            "360p", "480p" -> true
            else -> false
        }
    }
}
