package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * 转码视频文件：封装文件处理流程并更新聚合状态
 */
object TransferVideoFileCmd {

    private val logger = LoggerFactory.getLogger(TransferVideoFileCmd::class.java)

    @Service
    class Handler(
    ) : Command<Request, Response> {

        override fun exec(request: Request): Response {
            TODO("调整路径相关")
//            val videoDraft = request.videoDraft
//            val fileDraft = request.fileDraft
//
//            Mediator.uow.persist(fileDraft)
//
//            var duration: Int? = null
//            var fileSize: Long? = null
//            var outputPath: String? = null
//            var errorMessage: String? = null
//
//            try {
//                logger.info(
//                    "开始转码视频文件: videoId={}, uploadId={}, customerId={}",
//                    videoDraft.id,
//                    fileDraft.uploadId,
//                    fileDraft.customerId
//                )
//
//                fileDraft.startTransfer()
//                Mediator.uow.save()
//
//                val tempFilePath = buildTempFilePath(fileDraft.customerId, fileDraft.uploadId)
//                val targetFilePath = buildTargetFilePath(fileDraft.customerId, videoDraft.id)
//
//                val tempFileDir = File(tempFilePath)
//                require(tempFileDir.exists() && tempFileDir.isDirectory) {
//                    "临时文件目录不存在: $tempFilePath"
//                }
//
//                val targetFileDir = File(targetFilePath)
//                if (!targetFileDir.exists()) {
//                    targetFileDir.mkdirs()
//                }
//                copyDirectory(tempFileDir, targetFileDir)
//                logger.info("文件已复制到目标目录: {}", targetFilePath)
//
//                tempFileDir.deleteRecursively()
//                logger.info("临时目录已清理: {}", tempFilePath)
//
//                val mergedVideoPath = "$targetFilePath/merged_video.mp4"
//                mergeVideoChunks(targetFilePath, mergedVideoPath)
//                logger.info("视频分片合并完成: {}", mergedVideoPath)
//
//                duration = getVideoDuration(mergedVideoPath)
//                val codec = getVideoCodec(mergedVideoPath)
//                logger.info("视频时长: {}秒, 编码: {}", duration, codec)
//
//                val finalVideoPath = if (codec.equals("hevc", ignoreCase = true)) {
//                    val tempHevcPath = "$mergedVideoPath.hevc"
//                    File(mergedVideoPath).renameTo(File(tempHevcPath))
//                    logger.info("检测到 HEVC 编码，开始转码为 H.264")
//                    convertHevcToMp4(tempHevcPath, mergedVideoPath)
//                    File(tempHevcPath).delete()
//                    mergedVideoPath
//                } else {
//                    mergedVideoPath
//                }
//
//                val tsFolder = File(targetFilePath)
//                convertVideoToTs(tsFolder, finalVideoPath, videoProps.tsSegmentSeconds)
//                logger.info("视频已转换为 TS 格式: {}", targetFilePath)
//
//                File(finalVideoPath).delete()
//
//                fileSize = calculateTsFolderSize(tsFolder)
//                outputPath = targetFilePath
//
//                fileDraft.markTransferSuccess(duration, fileSize, targetFilePath)
//                logger.info("视频文件转码成功: fileDraftId={}", fileDraft.id)
//            } catch (ex: Exception) {
//                errorMessage = ex.message
//                fileDraft.markTransferFailed(errorMessage)
//                logger.error(
//                    "视频文件转码失败: videoId={}, uploadId={}, customerId={}",
//                    videoDraft.id,
//                    fileDraft.uploadId,
//                    fileDraft.customerId,
//                    ex
//                )
//                Mediator.uow.save()
//                return Response(success = false, errorMessage = errorMessage)
//            } finally {
//                Mediator.uow.save()
//            }
//
//            return Response(
//                success = true,
//                duration = duration,
//                fileSize = fileSize,
//                filePath = outputPath,
//            )
        }

        private fun buildTempFilePath(customerId: Long, uploadId: Long): String {
            TODO("调整路径相关")
//            return "${videoProps.tempFolder}/$customerId/$uploadId"
        }

        private fun buildTargetFilePath(customerId: Long, videoId: Long): String {
            TODO("调整路径相关")
//            return "${videoProps.targetFolder}/$customerId/$videoId"
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
                throw IllegalStateException("分片目录不存在: $dirPath")
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
        val videoPost: VideoPost,
        val fileDraft: VideoFilePost,
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean,
        val duration: Int? = null,
        val fileSize: Long? = null,
        val filePath: String? = null,
        val errorMessage: String? = null,
    )
}
