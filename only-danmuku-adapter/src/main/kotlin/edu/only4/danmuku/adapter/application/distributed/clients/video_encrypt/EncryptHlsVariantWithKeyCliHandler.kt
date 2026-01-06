package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import cn.hutool.core.util.RuntimeUtil
import com.only.engine.exception.KnownException
import com.only.engine.oss.core.OssClient
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsVariantWithKeyCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID

/**
 * 防腐层：按清晰度加密单档位 HLS 输出（不清空前缀）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Service
class EncryptHlsVariantWithKeyCliHandler : RequestHandler<EncryptHlsVariantWithKeyCli.Request, EncryptHlsVariantWithKeyCli.Response> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: EncryptHlsVariantWithKeyCli.Request): EncryptHlsVariantWithKeyCli.Response {
        var sourceTemp: Path? = null
        var outputTemp: Path? = null
        return runCatching {
            val quality = request.quality.trim()
            if (quality.isBlank()) {
                throw KnownException.illegalArgument("quality")
            }
            val sourcePrefix = normalizePrefix(request.sourceDir, "sourceDir")
            val outputPrefix = normalizePrefix(request.outputDir, "outputDir")

            val client = OssFactory.instance()
            val sourceContext = prepareSourceContext(sourcePrefix, quality, client).also { sourceTemp = it.tempPath }
            val outputContext = prepareOutputContext(outputPrefix).also { outputTemp = it.tempPath }
            val sourceDir = sourceContext.localDir
            val outputDir = outputContext.localDir
            if (outputDir.exists()) {
                outputDir.deleteRecursively()
            }
            outputDir.mkdirs()

            val playlistFile = File(sourceDir, "index.m3u8")
            if (!playlistFile.exists()) {
                throw KnownException("缺少清晰度播放列表: $quality")
            }

            val targetDir = File(outputDir, quality)
            if (targetDir.exists()) {
                targetDir.deleteRecursively()
            }
            targetDir.mkdirs()

            val keyBytes = hexToBytes(request.keyPlainHex)
            if (keyBytes.size != 16) {
                throw KnownException.illegalArgument("keyPlainHex")
            }
            val ivHex = resolveIvHex(request.ivHex)
            val keyInfo = buildKeyInfoFile(keyBytes, ivHex, quality, request.keyUriTemplate)
            val segmentPattern = "seg_%05d${request.segmentExt}"
            val targetPlaylist = File(targetDir, "index.m3u8").absolutePath
            val stdout = runFfmpegEncrypt(
                inputM3u8 = playlistFile.absolutePath,
                keyInfoPath = keyInfo.toAbsolutePath().toString(),
                segmentPattern = segmentPattern,
                targetPlaylist = targetPlaylist,
                workDir = targetDir,
                segmentDuration = detectSegmentDuration(playlistFile)
            )
            if (stdout.isNotBlank()) {
                logger.debug("ffmpeg encrypt stdout ({}): {}", quality, stdout.trim())
            }
            cleanupTempKeyDir(keyInfo.parent)

            val segmentCount = countSegments(targetDir, request.segmentExt)
            val variantPrefix = "${outputPrefix.trimEnd('/')}/$quality"
            client.deleteByPrefix(variantPrefix)
            uploadDirectory(client, targetDir, variantPrefix)

            EncryptHlsVariantWithKeyCli.Response(
                success = true,
                playlistPath = "$quality/index.m3u8",
                segmentPrefix = "$quality/",
                segmentCount = segmentCount,
                failReason = null
            )
        }.getOrElse {
            EncryptHlsVariantWithKeyCli.Response(
                success = false,
                playlistPath = null,
                segmentPrefix = null,
                segmentCount = null,
                failReason = it.message
            )
        }.also {
            cleanupTempDir(sourceTemp)
            cleanupTempDir(outputTemp)
        }
    }

    private fun normalizePrefix(prefix: String, field: String): String {
        val normalized = prefix.trim().trim('/')
        if (normalized.isBlank()) {
            throw KnownException.illegalArgument(field)
        }
        return normalized
    }

    private fun prepareSourceContext(sourcePrefix: String, quality: String, client: OssClient): StorageContext {
        val normalized = sourcePrefix.trimEnd('/')
        val qualityPrefix = "$normalized/$quality"
        val tempDir = Files.createTempDirectory("hls-src-").toFile()
        val downloaded = downloadByPrefix(client, qualityPrefix, tempDir.toPath())
        if (downloaded == 0) {
            throw KnownException.systemError("源目录不存在: $qualityPrefix")
        }
        return StorageContext(tempDir, qualityPrefix, tempDir.toPath())
    }

    private fun prepareOutputContext(outputPrefix: String): StorageContext {
        val normalized = outputPrefix.trimEnd('/')
        val tempDir = Files.createTempDirectory("hls-enc-").toFile()
        return StorageContext(tempDir, normalized, tempDir.toPath())
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

    private fun execForStdout(commands: List<String>, workDir: File? = null): String {
        if (commands.isEmpty()) {
            throw KnownException.illegalArgument("commands")
        }

        var process: Process? = null
        return try {
            val pb = ProcessBuilder(commands)
            if (workDir != null) {
                pb.directory(workDir)
            }
            process = pb.redirectErrorStream(true).start()
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

    private fun buildKeyInfoFile(
        keyBytes: ByteArray,
        ivHex: String,
        quality: String,
        keyUriTemplate: String?
    ): Path {
        val tmpDir = Files.createTempDirectory("hls-key-${UUID.randomUUID()}")
        val keyPath = tmpDir.resolve("enc.key")
        val keyInfoPath = tmpDir.resolve("key.info")
        Files.write(keyPath, keyBytes)
        val keyUri = keyUriTemplate?.takeIf { it.isNotBlank() }
            ?: "/video/enc/key?quality=$quality&token=__TOKEN__"
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

    private fun runFfmpegEncrypt(
        inputM3u8: String,
        keyInfoPath: String,
        segmentPattern: String,
        targetPlaylist: String,
        workDir: File,
        segmentDuration: Int
    ): String {
        val cmd = listOf(
            "ffmpeg",
            "-y",
            "-i", inputM3u8,
            "-c", "copy",
            "-f", "hls",
            "-hls_time", segmentDuration.toString(),
            "-hls_playlist_type", "vod",
            "-hls_flags", "independent_segments",
            "-hls_key_info_file", keyInfoPath,
            "-hls_segment_filename", segmentPattern,
            targetPlaylist
        )
        return execForStdout(cmd, workDir)
    }

    private fun countSegments(dir: File, segmentExt: String): Int {
        return dir.listFiles { f -> f.isFile && f.name.endsWith(segmentExt) }?.size ?: 0
    }

    private fun detectSegmentDuration(playlistFile: File): Int {
        if (!playlistFile.exists()) return 4
        return playlistFile.useLines { seq ->
            seq.firstOrNull { it.startsWith("#EXTINF:") }
                ?.removePrefix("#EXTINF:")
                ?.substringBefore(",")
                ?.toDoubleOrNull()
                ?.let { if (it > 0) it else null }
        }?.toInt() ?: 4
    }

    private fun uploadDirectory(client: OssClient, baseDir: File, prefix: String) {
        val basePath = baseDir.toPath()
        Files.walk(basePath).use { paths ->
            paths.filter { Files.isRegularFile(it) }.forEach { path ->
                val relative = basePath.relativize(path).toString().replace('\\', '/')
                val objectKey = "${prefix.trimEnd('/')}/$relative"
                val contentType = Files.probeContentType(path)
                path.toFile().inputStream().use { input ->
                    client.upload(input, objectKey, Files.size(path), contentType)
                }
            }
        }
    }

    private fun downloadByPrefix(client: OssClient, prefix: String, targetDir: Path): Int {
        val normalized = prefix.trim().trimEnd('/')
        val basePrefix = "$normalized/"
        val keys = client.listKeysByPrefix(basePrefix)
        if (keys.isEmpty()) {
            return 0
        }
        keys.forEach { key ->
            val relative = key.removePrefix(basePrefix).trimStart('/')
            val target = targetDir.resolve(relative)
            target.parent?.let { Files.createDirectories(it) }
            client.downloadToFile(key, target)
        }
        return keys.size
    }

    private fun cleanupTempKeyDir(dir: Path) {
        runCatching {
            Files.list(dir).use { stream ->
                stream.forEach { Files.deleteIfExists(it) }
            }
            Files.deleteIfExists(dir)
        }
    }

    private fun cleanupTempDir(path: Path?) {
        if (path == null) return
        runCatching { path.toFile().deleteRecursively() }
    }

    private data class StorageContext(
        val localDir: File,
        val objectPrefix: String,
        val tempPath: Path?,
    )
}

