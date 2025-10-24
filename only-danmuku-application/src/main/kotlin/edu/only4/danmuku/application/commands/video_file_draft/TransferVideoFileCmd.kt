package edu.only4.danmuku.application.commands.video_file_draft

import com.only.engine.misc.convertHevcToMp4
import com.only.engine.misc.convertVideoToTs
import com.only.engine.misc.getVideoCodec
import com.only.engine.misc.getVideoDuration
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_draft.SVideoDraft
import edu.only4.danmuku.domain._share.meta.video_file_draft.SVideoFileDraft
import edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.TransferResult
import org.slf4j.LoggerFactory
import edu.only4.danmuku.application._share.enums.config.properties.VideoAppProperties
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.jvm.optionals.getOrNull

/**
 * 转码视频文件
 *
 * 执行视频文件的下列操作：
 * - 将临时目录下的分片文件拷贝到正式目录
 * - 删除临时文件夹
 * - 合并分片文件
 * - 获取视频播放时长
 * - 检测视频编码格式，必要时转码为 H.264
 * - 转码视频文件为 TS 格式
 * - 更新文件和视频的状态
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object TransferVideoFileCmd {

    private val logger = LoggerFactory.getLogger(TransferVideoFileCmd::class.java)

    @Service
    class Handler(
        private val videoProps: VideoAppProperties,
    ) : Command<Request, Response> {

        override fun exec(request: Request): Response {
            var videoFileDraft: VideoFileDraft? = null
            var transferSuccess = false
            var errorMessage: String? = null

            try {
                // 1. 查询视频文件草稿信息
                videoFileDraft = Mediator.repositories.findOne(
                    SVideoFileDraft.predicate {
                        it.all(
                            (it.uploadId eq request.uploadId),
                            (it.customerId eq request.customerId)
                        )
                    }
                ).orElseThrow { IllegalArgumentException("视频文件不存在: uploadId=${request.uploadId}") }

                logger.info("开始转码视频文件: fileId=${videoFileDraft.fileId}, uploadId=${videoFileDraft.uploadId}")

                // 标记开始转码（领域方法）
                videoFileDraft.startTransfer()

                // 2. 构建文件路径
                val tempFilePath = buildTempFilePath(videoFileDraft.customerId, videoFileDraft.uploadId)
                val targetFilePath = buildTargetFilePath(videoFileDraft.customerId, videoFileDraft.videoId)

                val tempFileDir = File(tempFilePath)
                if (!tempFileDir.exists() || !tempFileDir.isDirectory) {
                    throw IllegalStateException("临时文件目录不存在: $tempFilePath")
                }

                // 3. 拷贝文件到正式目录
                val targetFileDir = File(targetFilePath)
                if (!targetFileDir.exists()) {
                    targetFileDir.mkdirs()
                }
                copyDirectory(tempFileDir, targetFileDir)
                logger.info("文件拷贝完成: $tempFilePath -> $targetFilePath")

                // 4. 删除临时目录
                tempFileDir.deleteRecursively()
                logger.info("临时目录已删除: $tempFilePath")

                // 5. 合并分片文件
                val mergedVideoPath = "$targetFilePath/merged_video.mp4"
                mergeVideoChunks(targetFilePath, mergedVideoPath)
                logger.info("视频分片合并完成: $mergedVideoPath")

                // 6. 获取视频时长
                val duration = getVideoDuration(mergedVideoPath)
                logger.info("视频时长: ${duration}秒")

                // 7. 获取视频编码并转码（如果需要）
                val codec = getVideoCodec(mergedVideoPath)
                logger.info("视频编码: $codec")

                val finalVideoPath = if (codec.equals("hevc", ignoreCase = true)) {
                    val tempHevcPath = "$mergedVideoPath.hevc"
                    File(mergedVideoPath).renameTo(File(tempHevcPath))

                    logger.info("检测到 HEVC 编码，开始转码为 H.264")
                    convertHevcToMp4(tempHevcPath, mergedVideoPath)

                    File(tempHevcPath).delete()
                    logger.info("HEVC 转码完成，临时文件已删除")
                    mergedVideoPath
                } else {
                    mergedVideoPath
                }

                // 8. 转码为 TS 格式
                val tsFolder = File(targetFilePath)
                convertVideoToTs(tsFolder, finalVideoPath, videoProps.tsSegmentSeconds)
                logger.info("视频已转码为 TS 格式: $targetFilePath")

                // 9. 删除合并后的原视频文件
                File(finalVideoPath).delete()
                logger.info("原始合并文件已删除: $finalVideoPath")

                // 10. 更新文件信息（领域方法）
                val fileSize = calculateTsFolderSize(tsFolder)
                videoFileDraft.markTransferSuccess(duration, fileSize, targetFilePath)
                transferSuccess = true

                logger.info("视频文件转码成功: fileId=${videoFileDraft.fileId}")

            } catch (e: Exception) {
                logger.error("视频文件转码失败: uploadId=${request.uploadId}", e)
                // 标记失败（领域方法）
                videoFileDraft?.markTransferFailed()
                errorMessage = e.message
            } finally {
                updateVideoDraftStatus(videoFileDraft!!.videoId)
                Mediator.uow.save()
            }

            return Response(
                success = transferSuccess,
                errorMessage = errorMessage
            )
        }

        /**
         * 构建临时文件路径
         */
        private fun buildTempFilePath(customerId: Long, uploadId: Long): String {
            return "${videoProps.tempFolder}/$customerId/$uploadId"
        }

        /**
         * 构建目标文件路径
         */
        private fun buildTargetFilePath(customerId: Long, videoId: Long): String {
            return "${videoProps.targetFolder}/$customerId/$videoId"
        }

        /**
         * 拷贝目录
         */
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

        /**
         * 合并视频分片
         */
        private fun mergeVideoChunks(dirPath: String, outputPath: String) {
            val dir = File(dirPath)
            if (!dir.exists() || !dir.isDirectory) {
                throw IllegalStateException("分片目录不存在: $dirPath")
            }

            val chunkFiles = dir.listFiles { file ->
                file.isFile && file.name.matches(Regex("\\d+"))
            }?.sortedBy { it.name.toIntOrNull() ?: 0 }
                ?: throw IllegalStateException("未找到分片文件")

            File(outputPath).outputStream().use { output ->
                chunkFiles.forEach { chunkFile ->
                    chunkFile.inputStream().use { input ->
                        input.copyTo(output)
                    }
                    chunkFile.delete() // 合并后删除分片
                }
            }
        }

        /**
         * 计算 TS 文件夹大小
         */
        private fun calculateTsFolderSize(folder: File): Long {
            return folder.walkTopDown()
                .filter { it.isFile }
                .map { it.length() }
                .sum()
        }

        /**
         * 更新视频草稿状态
         */
        private fun updateVideoDraftStatus(videoId: Long) {
            // 查询该视频的所有文件
            val allFiles = Mediator.repositories.find(
                SVideoFileDraft.predicate { it.videoId eq videoId }
            )

            // 检查是否有失败的文件
            val hasFailedFiles = allFiles.any { it.transferResult == TransferResult.FAILED }

            // 检查是否还有转码中的文件
            val hasTranscodingFiles = allFiles.any { it.transferResult == TransferResult.TRANSCODING }

            // 更新视频状态
            val videoDraft = Mediator.repositories.findOne(
                SVideoDraft.predicate { it.videoId eq videoId }
            ).getOrNull()

            if (videoDraft != null) {
                when {
                    hasFailedFiles -> {
                        // 有失败文件，标记为转码失败（领域方法）
                        videoDraft.markTranscodeFailed()
                        logger.info("视频转码失败: videoId=$videoId")
                    }

                    hasTranscodingFiles -> {
                        // 还有文件在转码中，保持转码中状态（领域方法）
                        videoDraft.markTranscoding()
                        logger.info("视频转码中: videoId=$videoId")
                    }

                    else -> {
                        // 所有文件都转码成功，标记为待审核
                        videoDraft.markPendingReview()

                        // 计算总时长并更新（领域方法）
                        val totalDuration = allFiles.mapNotNull { it.duration }.sum()
                        videoDraft.updateDuration(totalDuration)

                        logger.info("视频转码完成，待审核: videoId=$videoId, 总时长=${totalDuration}秒")
                    }
                }
            }
        }
    }

    class Request(
        val uploadId: Long,
        val customerId: Long,
    ) : RequestParam<Response>

    class Response(
        val success: Boolean = false,
        val errorMessage: String? = null,
    )
}
