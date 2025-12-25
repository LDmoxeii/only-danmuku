package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithQualityKeysCli
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry

import org.springframework.stereotype.Service

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
                val filePostId = request.videoFilePostId
                val qualities = Mediator.queries.send(
                    ListVideoAbrVariantsQry.Request(fileId = filePostId)
                ).firstOrNull()?.qualities ?: emptyList()
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
                        videoFilePostId = filePostId,
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
        val videoFilePostId: Long,
        val reason: String?
    ) : RequestParam<Response>

    data class Response(
        val newKeyVersion: Int,
        val failReason: String?
    )
}
