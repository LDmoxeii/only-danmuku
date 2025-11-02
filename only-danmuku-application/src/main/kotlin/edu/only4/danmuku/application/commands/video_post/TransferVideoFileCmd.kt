package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import edu.only4.danmuku.domain.aggregates.video_post.ports.VideoFileTranscodePort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import kotlin.jvm.optionals.getOrNull

object TransferVideoFileCmd {

    private val logger = LoggerFactory.getLogger(TransferVideoFileCmd::class.java)

    @Service
    class Handler(
        private val fileProps: FileAppProperties,
    ) : Command<Request, Response> {

        override fun exec(request: Request): Response {
            val videoPost = request.videoPost
            val file = request.videoFilePost

            val port = FFmpegVideoFileTranscodePort(fileProps)

            var errorMessage: String? = null

            runCatching {
                logger.info(
                    "开始转码单个文件 videoId={}, uploadId={}, idx={}",
                    videoPost.id, file.uploadId, file.fileIndex
                )
                file.transcode(videoPost.id, port)
                logger.info(
                    "完成转码单个文件 videoId={}, uploadId={}, idx={}, size={}, duration={}",
                    videoPost.id, file.uploadId, file.fileIndex, file.fileSize, file.duration
                )
            }.onFailure { e ->
                errorMessage = e.message
                logger.error(
                    "单文件转码失败 videoId={}, uploadId={}, idx={}, msg={}",
                    videoPost.id, file.uploadId, file.fileIndex, e.message, e
                )
            }

            // 转码结束后刷新视频草稿状态
            refreshDraftStatus(videoPost)

            Mediator.uow.persist(videoPost)
            Mediator.uow.save()

            return Response(
                success = errorMessage == null,
                duration = file.duration,
                fileSize = file.fileSize,
                filePath = file.filePath,
                errorMessage = errorMessage,
            )
        }

        private fun refreshDraftStatus(draft: VideoPost) {
            val hasFailedFiles = draft.videoFilePosts.any { it.isTransferFailed() }
            val hasTranscodingFiles = draft.videoFilePosts.any { it.isTranscoding() }

            when {
                hasFailedFiles -> draft.markTranscodeFailed()
                hasTranscodingFiles -> draft.markTranscoding()
                else -> {
                    draft.markPendingReview()
                    val totalDuration = draft.videoFilePosts.mapNotNull { it.duration }.sum()
                    draft.updateDuration(totalDuration)
                }
            }
        }
    }

    data class Request(
        val videoPost: VideoPost,
        val videoFilePost: VideoFilePost,
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean,
        val duration: Int? = null,
        val fileSize: Long? = null,
        val filePath: String? = null,
        val errorMessage: String? = null,
    )

    private class FFmpegVideoFileTranscodePort(
        private val fileProps: FileAppProperties,
    ) : VideoFileTranscodePort {
        override fun resolveTempDir(uploadId: Long): File {
            val session = Mediator.repositories.findFirst(
                SVideoFileUploadSession.predicateById(uploadId)
            ).getOrNull() ?: throw com.only.engine.exception.KnownException("文件不存在请重新上传")
            val tempPath = session.tempPath ?: throw com.only.engine.exception.KnownException("上传会话临时目录缺失")
            val fullPath = fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP + tempPath
            val dir = File(fullPath)
            require(dir.exists() && dir.isDirectory) { "临时文件目录不存在: $fullPath" }
            return dir
        }

        override fun resolveTargetDir(customerId: Long, videoId: Long, fileIndex: Int): Pair<File, String> {
            val relative = Constants.FILE_VIDEO + "$customerId/$videoId/$fileIndex"
            val fullPath = fileProps.projectFolder + Constants.FILE_FOLDER + relative
            val dir = File(fullPath)
            if (!dir.exists()) dir.mkdirs()
            return dir to relative
        }

        override fun newMergedOutputFile(targetDir: File): File {
            val name = Constants.TEMP_VIDEO_NAME.removePrefix("/")
            return File(targetDir, name)
        }

        override fun mergeChunks(sourceDir: File, outputFile: File) {
            val chunkFiles = sourceDir.listFiles { f -> f.isFile && f.name.matches(Regex("\\d+")) }
                ?.sortedBy { it.name.toIntOrNull() ?: 0 }
                ?: emptyList()
            if (chunkFiles.isEmpty()) {
                throw com.only.engine.exception.KnownException.systemError("未找到视频分片文件")
            }
            outputFile.outputStream().use { output ->
                chunkFiles.forEach { chunk ->
                    chunk.inputStream().use { input -> input.copyTo(output) }
                }
            }
            // 合并完成后删除分片
            chunkFiles.forEach { it.delete() }
        }

        override fun detectCodec(filePath: String): String =
            com.only.engine.misc.getVideoCodec(filePath, showLog = fileProps.showFFmpegLog)

        override fun transcodeHevcToH264(outputFilePath: String, inputFilePath: String) =
            com.only.engine.misc.convertHevcToMp4(outputFilePath, inputFilePath, showLog = fileProps.showFFmpegLog)

        override fun sliceToHls(tsFolder: File, inputMp4: File) =
            com.only.engine.misc.convertVideoToTs(tsFolder, inputMp4.absolutePath, showLog = fileProps.showFFmpegLog)

        override fun durationOf(videoFilePath: String): Int =
            com.only.engine.misc.getVideoDuration(videoFilePath, showLog = fileProps.showFFmpegLog)

        override fun folderSize(folder: File): Long =
            folder.walkTopDown().filter { it.isFile }.map { it.length() }.sum()

        override fun cleanupTempDir(sourceDir: File) {
            runCatching { sourceDir.deleteRecursively() }
        }

        override fun isHevc(codec: String): Boolean =
            codec.equals(Constants.VIDEO_CODE_HEVC, ignoreCase = true)
    }
}
