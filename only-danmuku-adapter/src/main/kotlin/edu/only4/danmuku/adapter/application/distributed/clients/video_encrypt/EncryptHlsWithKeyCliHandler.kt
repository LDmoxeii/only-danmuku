package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import cn.hutool.core.util.RuntimeUtil
import com.only4.cap4k.ddd.core.application.RequestHandler
import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithKeyCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.UUID

/**
 * 防腐层：使用指定 key 对已有 ABR 产物进行 HLS AES-128 加密，产出 enc/ 目录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class EncryptHlsWithKeyCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<EncryptHlsWithKeyCli.Request, EncryptHlsWithKeyCli.Response> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: EncryptHlsWithKeyCli.Request): EncryptHlsWithKeyCli.Response {
        return runCatching {
            val sourceDir = resolveToAbsolute(request.sourceDir)
            val outputDir = resolveToAbsolute(request.outputDir)
            val source = File(sourceDir)
            if (!source.exists() || !source.isDirectory) {
                throw KnownException.systemError("源目录不存在: $sourceDir")
            }
            val output = File(outputDir)
            if (output.exists()) {
                output.deleteRecursively()
            }
            output.mkdirs()

            val qualities = mutableListOf<VariantInfo>()
            val keyBytes = hexToBytes(request.keyPlainHex).also { if (it.size != 16) throw KnownException.illegalArgument("keyPlainHex") }
            val ivHex = resolveIvHex(request.ivHex)

            source.listFiles { file -> file.isDirectory }?.forEach { dir ->
                if (dir.name == "enc") return@forEach
                if (!request.quality.isNullOrBlank() && dir.name != request.quality) return@forEach
                val playlistFile = File(dir, "index.m3u8")
                if (!playlistFile.exists()) return@forEach

                val targetDir = File(output, dir.name)
                targetDir.mkdirs()

                // 使用 ffmpeg 对单档位进行加密重切片
                val keyInfo = buildKeyInfoFile(keyBytes, ivHex, request.keyId)
                val segmentPattern = File(targetDir, "seg_%05d${request.segmentExt}").absolutePath
                val targetPlaylist = File(targetDir, "index.m3u8").absolutePath
                val stdout = runFfmpegEncrypt(
                    inputM3u8 = playlistFile.absolutePath,
                    keyInfoPath = keyInfo.toAbsolutePath().toString(),
                    segmentPattern = segmentPattern,
                    targetPlaylist = targetPlaylist
                )
                if (stdout.isNotBlank()) {
                    logger.debug("ffmpeg encrypt stdout ({}): {}", dir.name, stdout.trim())
                }
                cleanupTempKeyDir(keyInfo.parent) // 清理临时 keyinfo/key 文件

                val segmentCount = countSegments(targetDir, request.segmentExt)
                qualities.add(
                    VariantInfo(
                        quality = dir.name,
                        playlistPath = "${dir.name}/index.m3u8",
                        segmentPrefix = "${dir.name}/",
                        segmentCount = segmentCount
                    )
                )
            }

            val master = File(source, "master.m3u8")
            if (master.exists()) {
                Files.copy(master.toPath(), File(output, "master.m3u8").toPath(), StandardCopyOption.REPLACE_EXISTING)
            }

            val variantsJson = JsonUtils.toJsonString(qualities) ?: "[]"
            EncryptHlsWithKeyCli.Response(
                success = true,
                encryptedMasterPath = toRelativePath(output),
                encryptedVariants = variantsJson,
                failReason = null
            )
        }.getOrElse {
            EncryptHlsWithKeyCli.Response(
                success = false,
                encryptedMasterPath = "",
                encryptedVariants = "[]",
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

    private fun hexToBytes(hex: String): ByteArray {
        val clean = hex.trim()
        require(clean.length % 2 == 0) { "invalid hex length" }
        return ByteArray(clean.length / 2) { i ->
            clean.substring(i * 2, i * 2 + 2).toInt(16).toByte()
        }
    }

    private fun resolveIvHex(ivHex: String?): String {
        return ivHex?.takeIf { it.isNotBlank() } ?: "00000000000000000000000000000000"
    }

    private fun execForStdout(commands: List<String>): String {
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

    private fun toRelativePath(output: File): String {
        val base = File(fileProps.projectFolder + Constants.FILE_FOLDER)
        return if (output.absolutePath.startsWith(base.absolutePath)) {
            output.absolutePath.removePrefix(base.absolutePath).trimStart('\\', '/')
                .let { if (it.isBlank()) "master.m3u8" else "$it/master.m3u8" }
        } else {
            File(output, "master.m3u8").absolutePath
        }
    }

    data class VariantInfo(
        val quality: String,
        val playlistPath: String,
        val segmentPrefix: String,
        val segmentCount: Int
    )

    private fun buildKeyInfoFile(keyBytes: ByteArray, ivHex: String, keyId: String): Path {
        val tmpDir = Files.createTempDirectory("hls-key-${UUID.randomUUID()}")
        val keyPath = tmpDir.resolve("enc.key")
        val keyInfoPath = tmpDir.resolve("key.info")
        Files.write(keyPath, keyBytes)
        val keyUri = "/video/enc/key?keyId=$keyId&token=__TOKEN__"
        Files.write(
            keyInfoPath,
            listOf(
                keyUri,
                keyPath.toAbsolutePath().toString(),
                ivHex
            )
        )
        return keyInfoPath
    }

    private fun runFfmpegEncrypt(inputM3u8: String, keyInfoPath: String, segmentPattern: String, targetPlaylist: String): String {
        val cmd = listOf(
            "ffmpeg",
            "-y",
            "-i", inputM3u8,
            "-c", "copy",
            "-f", "hls",
            "-hls_time", "4",
            "-hls_playlist_type", "vod",
            "-hls_flags", "independent_segments",
            "-hls_key_info_file", keyInfoPath,
            "-hls_segment_filename", segmentPattern,
            targetPlaylist
        )
        return execForStdout(cmd)
    }

    private fun countSegments(dir: File, segmentExt: String): Int {
        return dir.listFiles { f -> f.isFile && f.name.endsWith(segmentExt) }?.size ?: 0
    }

    private fun cleanupTempKeyDir(dir: Path) {
        runCatching {
            Files.list(dir).use { stream ->
                stream.forEach { Files.deleteIfExists(it) }
            }
            Files.deleteIfExists(dir)
        }
    }
}
