package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only.engine.misc.convertHevcToMp4
import com.only.engine.misc.convertVideoToTs
import com.only.engine.misc.getVideoCodec
import com.only.engine.misc.getVideoDuration
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.enums.config.properties.VideoAppProperties
import edu.only4.danmuku.domain._share.meta.video_draft.SVideoDraft
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.jvm.optionals.getOrNull

/**
 * 转码视频文件：封装文件处理流程并更新聚合状态
 */
object TransferVideoFileCmd {

    private val logger = LoggerFactory.getLogger(TransferVideoFileCmd::class.java)

    @Service
    class Handler(
        private val videoProps: VideoAppProperties,
    ) : Command<Request, Response> {

        override fun exec(request: Request): Response {
            val videoDraft = Mediator.repositories.findFirst(
                SVideoDraft.predicate { schema ->
                    schema.all(
                        schema.id eq request.videoId,
                        schema.customerId eq request.customerId
                    )
                },
                persist = true
            ).getOrNull() ?: throw KnownException("视频草稿不存在: ")

            val fileDraft = videoDraft.videoFileDrafts.firstOrNull { it.uploadId == request.uploadId }
                ?: throw KnownException("视频文件草稿不存在: uploadId=")

            var duration: Int?
            var fileSize: Long?
            var outputPath: String?
            var errorMessage: String?

            try {
                logger.info(
                    "开始转码视频文件: videoId={}, uploadId={}, customerId={}",
                    request.videoId,
                    request.uploadId,
                    request.customerId
                )

                fileDraft.startTransfer()

                val tempFilePath = buildTempFilePath(request.customerId, request.uploadId)
                val targetFilePath = buildTargetFilePath(request.customerId, request.videoId)

                val tempFileDir = File(tempFilePath)
                if (!tempFileDir.exists() || !tempFileDir.isDirectory) {
                    throw IllegalStateException("临时文件目录不存在: ")
                }

                val targetFileDir = File(targetFilePath)
                if (!targetFileDir.exists()) {
                    targetFileDir.mkdirs()
                }
                copyDirectory(tempFileDir, targetFileDir)
                logger.info("文件已复制到目标目录: {}", targetFilePath)

                tempFileDir.deleteRecursively()
                logger.info("临时目录已清理: {}", tempFilePath)

                val mergedVideoPath = "/merged_video.mp4"
                mergeVideoChunks(targetFilePath, mergedVideoPath)
                logger.info("视频分片合并完成: {}", mergedVideoPath)

                duration = getVideoDuration(mergedVideoPath)
                val codec = getVideoCodec(mergedVideoPath)
                logger.info("视频时长: {}秒, 编码: {}", duration, codec)

                val finalVideoPath = if (codec.equals("hevc", ignoreCase = true)) {
                    val tempHevcPath = ".hevc"
                    File(mergedVideoPath).renameTo(File(tempHevcPath))
                    logger.info("检测到 HEVC 编码，开始转码为 H.264")
                    convertHevcToMp4(tempHevcPath, mergedVideoPath)
                    File(tempHevcPath).delete()
                    mergedVideoPath
                } else {
                    mergedVideoPath
                }

                val tsFolder = File(targetFilePath)
                convertVideoToTs(tsFolder, finalVideoPath, videoProps.tsSegmentSeconds)
                logger.info("视频已转换为 TS 格式: {}", targetFilePath)

                File(finalVideoPath).delete()

                fileSize = calculateTsFolderSize(tsFolder)
                outputPath = targetFilePath

                fileDraft.markTransferSuccess(duration, fileSize, targetFilePath)
                logger.info("视频文件转码成功: fileDraftId={}", fileDraft.id)
            } catch (ex: Exception) {
                errorMessage = ex.message
                fileDraft.markTransferFailed(errorMessage)
                logger.error(
                    "视频文件转码失败: videoId={}, uploadId={}, customerId={}",
                    request.videoId,
                    request.uploadId,
                    request.customerId,
                    ex
                )
                return Response(success = false, errorMessage = errorMessage)
            } finally {
                Mediator.uow.save()
            }

            return Response(
                success = true,
                duration = duration,
                fileSize = fileSize,
                filePath = outputPath,
            )
        }

        private fun buildTempFilePath(customerId: Long, uploadId: Long): String {
            return "//"
        }

        private fun buildTargetFilePath(customerId: Long, videoId: Long): String {
            return "//"
        }

        private fun copyDirectory(source: File, target: File) {
            if (!target.exists()) {
                target.mkdirs()
            }
            source.listFiles()?.forEach { file ->
                val targetFile = File(target, file.name)
                if (file.isDirectory) {
                    copyDirectory(file, targetFile)
                } else {
                    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                }
            }
        }

        private fun mergeVideoChunks(dirPath: String, outputPath: String) {
            val dir = File(dirPath)
            if (!dir.exists() || !dir.isDirectory) {
                throw IllegalStateException("分片目录不存在: ")
            }

            val chunkFiles = dir.listFiles { file ->
                file.isFile && file.name.matches(Regex("\\d+"))
            }?.sortedBy { it.name.toIntOrNull() ?: 0 }
                ?: throw IllegalStateException("未找到视频分片文件")

            File(outputPath).outputStream().use { output ->
                chunkFiles.forEach { chunkFile ->
                    chunkFile.inputStream().use { input -> input.copyTo(output) }
                    chunkFile.delete()
                }
            }
        }

        private fun calculateTsFolderSize(folder: File): Long {
            return folder.walkTopDown()
                .filter { it.isFile }
                .map { it.length() }
                .sum()
        }
    }

    data class Request(
        val videoId: Long,
        val uploadId: Long,
        val customerId: Long,
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean,
        val duration: Int? = null,
        val fileSize: Long? = null,
        val filePath: String? = null,
        val errorMessage: String? = null,
    )
}
