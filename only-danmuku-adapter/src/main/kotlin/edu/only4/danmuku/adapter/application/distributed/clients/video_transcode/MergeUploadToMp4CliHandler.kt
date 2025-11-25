package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only.engine.exception.KnownException
import com.only.engine.misc.convertHevcToMp4
import com.only.engine.misc.getVideoCodec
import com.only.engine.misc.getVideoDuration
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.video_transcode.MergeUploadToMp4Cli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

/**
 * 防腐层：合并上传分片为临时 MP4，返回相对输出目录、时长、文件大小
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class MergeUploadToMp4CliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<MergeUploadToMp4Cli.Request, MergeUploadToMp4Cli.Response> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: MergeUploadToMp4Cli.Request): MergeUploadToMp4Cli.Response {
        return runCatching {
            val tempPath = request.tempPath
            val sourceDir = File(fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP + tempPath)
            if (!sourceDir.exists() || !sourceDir.isDirectory) {
                throw KnownException("临时目录不存在: ${sourceDir.absolutePath}")
            }

            val relativeOutput = "${Constants.FILE_VIDEO}${request.customerId}/${request.videoId}/${request.fileIndex}"
            val outputDir = File(fileProps.projectFolder + Constants.FILE_FOLDER + relativeOutput).also { it.mkdirs() }
            val mergedMp4 = File(outputDir, Constants.TEMP_VIDEO_NAME.removePrefix("/"))

            mergeChunks(sourceDir, mergedMp4)

            val codec = getVideoCodec(mergedMp4.absolutePath, showLog = fileProps.showFFmpegLog)
            if (codec.equals(Constants.VIDEO_CODE_HEVC, ignoreCase = true)) {
                val hevcBackup = File(outputDir, mergedMp4.name + ".hevc")
                mergedMp4.renameTo(hevcBackup)
                convertHevcToMp4(mergedMp4.absolutePath, hevcBackup.absolutePath, showLog = fileProps.showFFmpegLog)
                hevcBackup.delete()
            }

            val duration = getVideoDuration(mergedMp4.absolutePath, showLog = fileProps.showFFmpegLog)
            val size = mergedMp4.length()

            // 不删除 mergedMp4，后续 ABR 直接使用
            MergeUploadToMp4Cli.Response(
                success = true,
                outputDir = relativeOutput,
                mergedMp4Path = mergedMp4.absolutePath,
                duration = duration,
                fileSize = size,
            )
        }.getOrElse { ex ->
            logger.error("合并上传分片失败", ex)
            MergeUploadToMp4Cli.Response(
                success = false,
                failReason = ex.message
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
}

