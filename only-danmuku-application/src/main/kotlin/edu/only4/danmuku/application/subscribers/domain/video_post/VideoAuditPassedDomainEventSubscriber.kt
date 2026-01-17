package edu.only4.danmuku.application.subscribers.domain.video_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.RewardUserForVideoCmd
import edu.only4.danmuku.application.commands.video.TransferVideoToProductionCmd
import edu.only4.danmuku.application.commands.video_encrypt.BindVideoHlsEncryptKeyToVideoCmd
import edu.only4.danmuku.application.commands.video_encrypt.BindVideoHlsKeyTokenToVideoCmd
import edu.only4.danmuku.application.commands.video_quality_policy.SetVideoQualityPolicyCmd
import edu.only4.danmuku.application.distributed.clients.CleanTempFilesCli
import edu.only4.danmuku.application.queries.video_file_upload_session.GetUploadedTempPathsQry
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsEncryptKeysByPostFileQry
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsKeyTokensByPostFileQry
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoAuditPassedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_quality_policy.enums.QualityAuthPolicy
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 视频审核通过后的联动逻辑
 */
@Service
class VideoAuditPassedDomainEventSubscriber {

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun rewardUserForVideo(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            RewardUserForVideoCmd.Request(
                customerId = videoPost.customerId,
            )
        )
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun transferVideoToProduction(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            TransferVideoToProductionCmd.Request(
                videoPostId = videoPost.id,
                customerId = videoPost.customerId,
                videoCover = videoPost.videoCover,
                videoName = videoPost.videoName,
                parentCategoryId = videoPost.pCategoryId,
                categoryId = videoPost.categoryId,
                postType = videoPost.postType.code,
                originInfo = videoPost.originInfo,
                tags = videoPost.tags,
                introduction = videoPost.introduction,
                interaction = videoPost.interaction,
                duration = videoPost.duration,
            )
        )
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun on2(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        val tempPaths = Mediator.queries.send(
            GetUploadedTempPathsQry.Request(
                customerId = videoPost.customerId,
                videoId = videoPost.id,
            )
        )

        Mediator.requests.send(
            CleanTempFilesCli.Request(
                tempPaths = tempPaths
            )
        )
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun bindVideoHlsEncryptKeyToVideo(event: VideoAuditPassedDomainEvent) {
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
        }
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun bindVideoHlsKeyTokenToVideo(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        val video = Mediator.repositories.findFirst(
            SVideo.predicate { schema ->
                schema.videoPostId.eq(videoPost.id)
            }
        ).getOrNull() ?: return

        videoPost.videoFilePosts.forEach { file ->

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
        }
    }

    @EventListener(VideoAuditPassedDomainEvent::class)
    fun setVideoQualityPolicy(event: VideoAuditPassedDomainEvent) {
        val videoPost = event.entity
        val video = Mediator.repositories.findFirst(
            SVideo.predicate { schema ->
                schema.videoPostId.eq(videoPost.id)
            }
        ).getOrNull() ?: return

        videoPost.videoFilePosts.forEach { file ->
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
