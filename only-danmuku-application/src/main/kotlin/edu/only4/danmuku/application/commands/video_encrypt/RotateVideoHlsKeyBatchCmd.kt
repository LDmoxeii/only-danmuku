package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithQualityKeysCli
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 批量轮换所有清晰度 key 并触发重加密
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object RotateVideoHlsKeyBatchCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            return runCatching {
                val videoPost = Mediator.repositories.findOne(
                    SVideoPost.predicateById(request.videoPostId)
                ).getOrNull() ?: throw IllegalStateException("稿件不存在: ${request.videoPostId}")
                val file = videoPost.videoFilePosts.firstOrNull { it.fileIndex == request.fileIndex }
                    ?: throw IllegalStateException("分P不存在: videoPostId=${request.videoPostId}, fileIndex=${request.fileIndex}")
                val qualities = file.videoFilePostVariants
                    .map { it.quality }
                    .mapNotNull { it.trim().takeIf(String::isNotBlank) }
                    .distinct()
                if (qualities.isEmpty()) {
                    throw IllegalStateException("未找到可用清晰度: videoPostId=${request.videoPostId}, fileIndex=${request.fileIndex}")
                }
                val path = file.filePath
                    ?: throw IllegalStateException("filePath 为空: videoPostId=${request.videoPostId}, fileIndex=${request.fileIndex}")

                val generated = Mediator.commands.send(
                    GenerateVideoPostQualityKeysCmd.Request(
                        videoPostId = request.videoPostId,
                        fileIndex = request.fileIndex,
                        qualities = qualities
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
                        videoPostId = request.videoPostId,
                        fileIndex = request.fileIndex,
                        success = encrypted.success,
                        keyVersion = generated.keyVersion,
                        encryptedMasterPath = encrypted.encryptedMasterPath,
                        encryptedVariants = encrypted.encryptedVariants,
                        failReason = encrypted.failReason
                    )
                )

                Response(
                    newKeyVersion = generated.keyVersion,
                    failReason = encrypted.failReason
                )
            }.getOrElse { ex ->
                Response(
                    newKeyVersion = 0,
                    failReason = ex.message
                )
            }
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val reason: String?
    ) : RequestParam<Response>

    data class Response(
        val newKeyVersion: Int,
        val failReason: String?
    )
}
