package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import cn.hutool.core.util.RuntimeUtil
import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import com.only.engine.misc.FFprobeUtils
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.video_transcode.TranscodeVideoFileToAbrCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

/**
 * 防腐层：调用 FFmpeg/脚本生成多分辨率 HLS 与 master.m3u8
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class TranscodeVideoFileToAbrCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<TranscodeVideoFileToAbrCli.Request, TranscodeVideoFileToAbrCli.Response> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: TranscodeVideoFileToAbrCli.Request): TranscodeVideoFileToAbrCli.Response {
        return runCatching {
            val sourcePath = resolveToAbsolute(request.sourcePath)
            val outputBaseDir = resolveToAbsolute(request.outputDir)
            val segmentDuration = request.segmentDurationSec.coerceAtLeast(1)

            val sourceFile = File(sourcePath)
            if (!sourceFile.exists()) {
                throw KnownException.systemError("源视频不存在: $sourcePath")
            }
            val outputDir = File(outputBaseDir)
            if (outputDir.exists().not() && !outputDir.mkdirs()) {
                throw KnownException.systemError("无法创建输出目录: ${outputDir.absolutePath}")
            }

            val sourceProbe =
                FFprobeUtils.probeVideoResolution(sourceFile.absolutePath, showLog = fileProps.showFFmpegLog)
            val profiles = parseProfiles(request.profiles)
            val filteredProfiles = profiles.filter { profile ->
                profile.height <= sourceProbe.height && profile.width > 0 && profile.height > 0
            }
            if (filteredProfiles.isEmpty()) {
                return TranscodeVideoFileToAbrCli.Response(
                    accepted = false,
                    variants = "[]",
                    failReason = "源视频分辨率过低，无法匹配任何预置档位"
                )
            }

            val variantOutputs = mutableListOf<AbrVariantOutput>()
            filteredProfiles.forEach { profile ->
                val variantDir = File(outputDir, profile.quality)
                if (variantDir.exists()) {
                    variantDir.deleteRecursively()
                }
                variantDir.mkdirs()

                val command = buildFfmpegCommand(
                    input = sourceFile.absolutePath,
                    variantDir = variantDir,
                    height = profile.height,
                    videoBitrateKbps = profile.videoBitrateKbps,
                    audioBitrateKbps = profile.audioBitrateKbps,
                    segmentDuration = segmentDuration
                )
                val stdout = execForStdout(command)
                if (stdout.isNotBlank()) {
                    logger.debug("ffmpeg stdout ({}): {}", profile.quality, stdout.trim())
                }

                val playlistPath = "${profile.quality}/index.m3u8"
                variantOutputs.add(
                    AbrVariantOutput(
                        quality = profile.quality,
                        width = profile.width,
                        height = profile.height,
                        videoBitrateKbps = profile.videoBitrateKbps,
                        audioBitrateKbps = profile.audioBitrateKbps,
                        bandwidthBps = (profile.videoBitrateKbps + profile.audioBitrateKbps) * 1000,
                        playlistPath = playlistPath,
                        segmentPrefix = "${profile.quality}/",
                        segmentDuration = segmentDuration,
                    )
                )
            }

            if (variantOutputs.isEmpty()) {
                return TranscodeVideoFileToAbrCli.Response(
                    accepted = false,
                    variants = "[]",
                    failReason = "未生成任何 ABR 档位"
                )
            }

            writeMasterPlaylist(outputDir, variantOutputs)

            val variantsJson = JsonUtils.toJsonString(variantOutputs) ?: "[]"
            TranscodeVideoFileToAbrCli.Response(
                accepted = true,
                variants = variantsJson,
                failReason = null
            )
        }.getOrElse {
            logger.error("ABR 转码失败", it)
            TranscodeVideoFileToAbrCli.Response(
                accepted = false,
                variants = "[]",
                failReason = it.message
            )
        }
    }

    private fun resolveToAbsolute(path: String): String {
        val file = File(path)
        if (file.isAbsolute) {
            return file.absolutePath
        }
        return File(fileProps.projectFolder + Constants.FILE_FOLDER + path).absolutePath
    }

    private fun parseProfiles(json: String): List<AbrProfile> {
        val profiles = JsonUtils.parseArray(json, AbrProfile::class.java)
        if (profiles.isEmpty()) {
            throw KnownException.illegalArgument("profiles")
        }
        return profiles
    }

    private fun buildFfmpegCommand(
        input: String,
        variantDir: File,
        height: Int,
        videoBitrateKbps: Int,
        audioBitrateKbps: Int,
        segmentDuration: Int,
    ): List<String> {
        val segmentPattern = File(variantDir, "%04d.ts").absolutePath
        val playlistFile = File(variantDir, "index.m3u8").absolutePath
        return listOf(
            "ffmpeg",
            "-y",
            "-i",
            input,
            "-vf",
            "scale=-2:$height",
            "-c:v",
            "h264",
            "-profile:v",
            "high",
            "-preset",
            "veryfast",
            "-b:v",
            "${videoBitrateKbps}k",
            "-maxrate",
            "${videoBitrateKbps}k",
            "-bufsize",
            "${videoBitrateKbps * 2}k",
            "-c:a",
            "aac",
            "-ar",
            "48000",
            "-b:a",
            "${audioBitrateKbps}k",
            "-ac",
            "2",
            "-hls_time",
            segmentDuration.toString(),
            "-hls_playlist_type",
            "vod",
            "-hls_segment_filename",
            segmentPattern,
            playlistFile
        )
    }

    fun execForStdout(commands: List<String>): String {
        if (commands.isEmpty()) {
            throw KnownException.illegalArgument("commands")
        }

        var process: Process? = null
        return try {
            process = RuntimeUtil.exec(*commands.toTypedArray())
            val stdout = RuntimeUtil.getResult(process)
            val stderr = RuntimeUtil.getErrorResult(process)
            val exitCode = process.waitFor()

            val commandLine = commands.joinToString(" ")
            logger.debug("FFmpeg command executed: {} (exitCode={})", commandLine, exitCode)
            if (stderr.isNotBlank()) {
                logger.debug("FFmpeg stderr: {}", stderr.trim())
            }
            if (exitCode != 0) {
                val message = buildString {
                    append("FFmpeg 命令执行失败 (exitCode=").append(exitCode).append(")")
                    if (stderr.isNotBlank()) {
                        append(": ").append(stderr.trim())
                    }
                }
                throw KnownException.systemError(message)
            }

            stdout
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw KnownException.systemError(e)
        } catch (e: KnownException) {
            throw e
        } catch (e: Exception) {
            throw KnownException.systemError(e)
        } finally {
            process?.destroy()
        }
    }

    private fun writeMasterPlaylist(outputDir: File, variants: List<AbrVariantOutput>) {
        val masterFile = File(outputDir, "master.m3u8")
        masterFile.printWriter().use { writer ->
            writer.println("#EXTM3U")
            writer.println("#EXT-X-VERSION:3")
            variants.forEach { variant ->
                writer.println("#EXT-X-STREAM-INF:BANDWIDTH=${variant.bandwidthBps},RESOLUTION=${variant.width}x${variant.height}")
                writer.println(variant.playlistPath)
            }
        }
    }

    data class AbrProfile(
        val quality: String,
        val width: Int,
        val height: Int,
        val videoBitrateKbps: Int,
        val audioBitrateKbps: Int,
    )

    data class AbrVariantOutput(
        val quality: String,
        val width: Int,
        val height: Int,
        val videoBitrateKbps: Int,
        val audioBitrateKbps: Int,
        val bandwidthBps: Int,
        val playlistPath: String,
        val segmentPrefix: String,
        val segmentDuration: Int,
    )
}
