package edu.only4.danmuku.adapter.application.distributed.clients

import com.only.engine.exception.KnownException
import com.only.engine.misc.convertHevcToMp4
import com.only.engine.misc.convertVideoToTs
import com.only.engine.misc.getVideoCodec
import com.only.engine.misc.getVideoDuration
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.TranscodeVideoFileCli
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import org.springframework.stereotype.Service
import java.io.File
import kotlin.jvm.optionals.getOrNull

/**
 * 防腐层：对单个上传文件执行转码，返回成功/失败、输出路径、时长
 */
@Service
class TranscodeVideoFileCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<TranscodeVideoFileCli.Request, TranscodeVideoFileCli.Response> {

    override fun exec(request: TranscodeVideoFileCli.Request): TranscodeVideoFileCli.Response {
        val session = Mediator.repositories.findFirst(
            SVideoFileUploadSession.predicateById(request.uploadId),
            persist = false
        ).getOrNull() ?: throw KnownException("文件不存在，请重新上传")

        val tempPath = session.tempPath ?: throw KnownException("上传会话临时目录缺失")
        val sourceDir = File(fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP + tempPath)
        if (!sourceDir.exists() || !sourceDir.isDirectory) {
            throw KnownException("临时目录不存在: ${sourceDir.absolutePath}")
        }

        val relative = "${Constants.FILE_VIDEO}${request.customerId}/${request.videoId}/${request.fileIndex}"
        val targetDir = File(fileProps.projectFolder + Constants.FILE_FOLDER + relative).also { it.mkdirs() }
        val mergedMp4 = File(targetDir, Constants.TEMP_VIDEO_NAME.removePrefix("/"))

        return runCatching {
            mergeChunks(sourceDir, mergedMp4)

            val codec = getVideoCodec(mergedMp4.absolutePath, showLog = fileProps.showFFmpegLog)
            if (codec.equals(Constants.VIDEO_CODE_HEVC, ignoreCase = true)) {
                val hevcBackup = File(targetDir, mergedMp4.name + ".hevc")
                mergedMp4.renameTo(hevcBackup)
                convertHevcToMp4(mergedMp4.absolutePath, hevcBackup.absolutePath, showLog = fileProps.showFFmpegLog)
                hevcBackup.delete()
            }

            val duration = getVideoDuration(mergedMp4.absolutePath, showLog = fileProps.showFFmpegLog)
            convertVideoToTs(targetDir, mergedMp4.absolutePath, showLog = fileProps.showFFmpegLog)
            mergedMp4.delete()

            val size = folderSize(targetDir)
            sourceDir.deleteRecursively()

            TranscodeVideoFileCli.Response(
                success = true,
                outputPath = relative,
                duration = duration,
                fileSize = size,
            )
        }.getOrElse { e ->
            runCatching { mergedMp4.delete() }
            TranscodeVideoFileCli.Response(
                success = false,
                failReason = e.message
            )
        }
    }

    private fun mergeChunks(sourceDir: File, outputFile: File) {
        val chunkFiles = sourceDir.listFiles { f -> f.isFile && f.name.matches(Regex("\\d+")) }
            ?.sortedBy { it.name.toIntOrNull() ?: 0 } ?: emptyList()
        if (chunkFiles.isEmpty()) throw KnownException("未找到视频分片文件")
        outputFile.outputStream().use { output ->
            chunkFiles.forEach { chunk ->
                chunk.inputStream().use { input -> input.copyTo(output) }
            }
        }
        chunkFiles.forEach { it.delete() }
    }

    private fun folderSize(folder: File): Long =
        folder.walkTopDown().filter { it.isFile }.map { it.length() }.sum()
}
