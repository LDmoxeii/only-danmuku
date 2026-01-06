package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only.engine.exception.KnownException
import com.only.engine.misc.convertHevcToMp4
import com.only.engine.misc.getVideoCodec
import com.only.engine.misc.getVideoDuration
import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.video_transcode.MergeUploadToMp4ByPathCli

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files

/**
 * 防腐层：按路径合并分片为 MP4（不接触数据库ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class MergeUploadToMp4ByPathCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<MergeUploadToMp4ByPathCli.Request, MergeUploadToMp4ByPathCli.Response> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: MergeUploadToMp4ByPathCli.Request): MergeUploadToMp4ByPathCli.Response {
        return runCatching {
            val sourceDir = resolveTempDir(request.tempPath)
            if (!sourceDir.exists() || !sourceDir.isDirectory) {
                throw KnownException("临时目录不存在: ${sourceDir.absolutePath}")
            }

            val outputDir = File(request.outputDir)
            if (outputDir.exists().not() && !outputDir.mkdirs()) {
                throw KnownException("无法创建输出目录: ${outputDir.absolutePath}")
            }
            val mergedMp4 = Files.createTempFile(outputDir.toPath(), "merge-", ".mp4").toFile()

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

            MergeUploadToMp4ByPathCli.Response(
                success = true,
                outputDir = outputDir.absolutePath,
                mergedMp4Path = mergedMp4.absolutePath,
                duration = duration,
                fileSize = size,
                failReason = null
            )
        }.getOrElse { ex ->
            logger.error("合并上传分片失败", ex)
            MergeUploadToMp4ByPathCli.Response(
                success = false,
                outputDir = request.outputDir,
                mergedMp4Path = "",
                duration = null,
                fileSize = null,
                failReason = ex.message
            )
        }.also {
            cleanupTempDir(request.tempPath)
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

    private fun resolveTempDir(tempPath: String): File {
        val f = File(tempPath)
        if (f.isAbsolute) {
            return f
        }
        throw KnownException("临时目录必须为绝对路径")
    }

    private fun cleanupTempDir(tempPath: String) {
        runCatching {
            val target = resolveTempDir(tempPath)
            if (!target.exists()) return
            val ok = target.deleteRecursively()
            if (!ok) {
                logger.warn("清理临时目录失败: {}", target.absolutePath)
            }
        }.onFailure { ex ->
            logger.warn("清理临时目录失败: {}", tempPath, ex)
        }
    }
}

