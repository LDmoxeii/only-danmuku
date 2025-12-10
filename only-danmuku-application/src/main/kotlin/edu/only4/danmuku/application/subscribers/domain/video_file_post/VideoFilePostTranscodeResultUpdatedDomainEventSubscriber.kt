package edu.only4.danmuku.application.subscribers.domain.video_file_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostTranscodeResultUpdatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.commands.video_post.RefreshVideoPostTranscodeStatusCmd
import edu.only4.danmuku.application.commands.video_encrypt.GenerateVideoHlsKeyCmd
import edu.only4.danmuku.application.commands.video_encrypt.PersistVideoEncryptResultCmd
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithKeyCli
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptStatus
import java.util.Base64

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
                val fileId = event.entity.id
                val path = Mediator.queries.send(
                    GetVideoFilePostPathQry.Request(filePostId = fileId)
                ).filePath ?: return

                val keyResp = Mediator.commands.send(
                    GenerateVideoHlsKeyCmd.Request(
                        videoFilePostId = fileId,
                        quality = null,
                        method = 1
                    )
                )

                val encResp = Mediator.requests.send(
                    EncryptHlsWithKeyCli.Request(
                        videoFilePostId = fileId,
                        sourceDir = path,
                        outputDir = "$path/enc",
                        keyId = keyResp.keyId,
                        quality = null,
                        keyPlainHex = decodeBase64ToHex(keyResp.keyCiphertextBase64),
                        ivHex = keyResp.ivHex,
                        segmentExt = ".ts.enc"
                    )
                )

                Mediator.commands.send(
                    PersistVideoEncryptResultCmd.Request(
                        videoFilePostId = fileId,
                        success = encResp.success,
                        encryptMethod = 1,
                        keyId = keyResp.keyId,
                        keyVersion = keyResp.keyVersion,
                        keyQuality = null,
                        encryptedMasterPath = encResp.encryptedMasterPath,
                        encryptedVariants = encResp.encryptedVariants,
                        failReason = encResp.failReason
                    )
                )
            }
        }
    }

    private fun decodeBase64ToHex(cipherBase64: String): String {
        val bytes = Base64.getDecoder().decode(cipherBase64)
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
