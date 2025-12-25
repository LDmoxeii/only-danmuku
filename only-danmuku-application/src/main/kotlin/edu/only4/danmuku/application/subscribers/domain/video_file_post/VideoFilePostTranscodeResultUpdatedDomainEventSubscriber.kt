package edu.only4.danmuku.application.subscribers.domain.video_file_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostTranscodeResultUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.commands.video_post.RefreshVideoPostTranscodeStatusCmd
import edu.only4.danmuku.application.commands.video_encrypt.GenerateVideoHlsQualityKeysCmd
import edu.only4.danmuku.application.commands.video_encrypt.PersistVideoEncryptBatchResultCmd
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithQualityKeysCli
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptStatus
import org.slf4j.LoggerFactory

/**
 * 单个分P转码结果已回写事件，驱动稿件状态刷新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoFilePostTranscodeResultUpdatedDomainEventSubscriber {
    private val logger = LoggerFactory.getLogger(VideoFilePostTranscodeResultUpdatedDomainEventSubscriber::class.java)

    @EventListener(VideoFilePostTranscodeResultUpdatedDomainEvent::class)
    fun on(event: VideoFilePostTranscodeResultUpdatedDomainEvent) {
        Mediator.commands.send(
            RefreshVideoPostTranscodeStatusCmd.Request(
                videoPostId = event.entity.videoId
            )
        )
    }

    @EventListener(VideoFilePostTranscodeResultUpdatedDomainEvent::class)
    fun encryptVideoFile(event: VideoFilePostTranscodeResultUpdatedDomainEvent) {
        if (event.entity.transferResult == TransferResult.SUCCESS && event.entity.encryptStatus == EncryptStatus.UNENCRYPTED) {
            runCatching {
                val filePostId = event.entity.id
                val qualities = event.entity.videoHlsAbrVariants
                    .mapNotNull { it.quality.takeIf(String::isNotBlank) }
                    .distinct()
                if (qualities.isEmpty()) {
                    throw IllegalStateException("未找到可用清晰度: $filePostId")
                }
                val path = Mediator.queries.send(
                    GetVideoFilePostPathQry.Request(filePostId = filePostId)
                ).filePath ?: throw IllegalStateException("filePath 为空: $filePostId")

                val generated = Mediator.commands.send(
                    GenerateVideoHlsQualityKeysCmd.Request(
                        videoFilePostId = filePostId,
                        videoFileId = null,
                        qualities = qualities,
                        method = event.entity.encryptMethod.name
                    )
                )

                val encrypted = Mediator.requests.send(
                    EncryptHlsWithQualityKeysCli.Request(
                        sourceDir = path,
                        outputDir = "$path/enc",
                        keysJson = generated.keysJson,
                        segmentExt = ".ts.enc"
                    )
                )

                Mediator.commands.send(
                    PersistVideoEncryptBatchResultCmd.Request(
                        videoFilePostId = filePostId,
                        success = encrypted.success,
                        encryptMethod = event.entity.encryptMethod.name,
                        keyVersion = generated.keyVersion,
                        encryptedMasterPath = encrypted.encryptedMasterPath,
                        encryptedVariants = encrypted.encryptedVariants,
                        failReason = encrypted.failReason
                    )
                )
            }.onFailure { ex ->
                logger.error("EncryptHlsWithQualityKeys failed for filePostId=${event.entity.id}", ex)
                Mediator.commands.send(
                    PersistVideoEncryptBatchResultCmd.Request(
                        videoFilePostId = event.entity.id,
                        success = false,
                        encryptMethod = event.entity.encryptMethod.name,
                        keyVersion = 0,
                        encryptedMasterPath = null,
                        encryptedVariants = null,
                        failReason = ex.message
                    )
                )
            }
        }
    }
}
