package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import cn.hutool.core.util.RuntimeUtil
import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import com.only.engine.oss.core.OssClient
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithQualityKeysCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.Base64
import java.util.UUID

/**
 * 防腐层：按清晰度使用独立 key 加密 ABR 输出并生成 enc/master
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class EncryptHlsWithQualityKeysCliHandler :
    RequestHandler<EncryptHlsWithQualityKeysCli.Request, EncryptHlsWithQualityKeysCli.Response> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: EncryptHlsWithQualityKeysCli.Request): EncryptHlsWithQualityKeysCli.Response {
        var sourceTemp: Path? = null
        var outputTemp: Path? = null
        return runCatching {
            val keyPayloads = parseKeys(request.keysJson)
            if (keyPayloads.isEmpty()) {
                throw KnownException.illegalArgument("keysJson")
            }

            val client = OssFactory.instance()
            val sourceContext = prepareSourceContext(request.sourceDir, client).also { sourceTemp = it.tempPath }
            val outputContext = prepareOutputContext(request.outputDir).also { outputTemp = it.tempPath }
            val source = sourceContext.localDir
            val output = outputContext.localDir
            if (output.exists()) {
                output.deleteRecursively()
            }
            output.mkdirs()

            val playlistDurationSec = detectSegmentDuration(source)
            val variants = mutableListOf<VariantInfo>()
            val processed = mutableSetOf<String>()

            keyPayloads.forEach { payload ->
                val quality = payload.quality.trim()
                if (quality.isBlank() || !processed.add(quality)) {
                    return@forEach
                }
                val sourceDir = File(source, quality)
                val playlistFile = File(sourceDir, "index.m3u8")
                if (!playlistFile.exists()) {
                    throw KnownException("缺少清晰度播放列表: $quality")
                }

                val targetDir = File(output, quality)
                if (targetDir.exists()) {
                    targetDir.deleteRecursively()
                }
                targetDir.mkdirs()

                val keyBytes = resolveKeyBytes(payload)
                if (keyBytes.size != 16) {
                    throw KnownException.illegalArgument("keyPlainHex")
                }
                val ivHex = resolveIvHex(payload.ivHex)
                val keyInfo = buildKeyInfoFile(keyBytes, ivHex, payload)

                val segmentPattern = "seg_%05d${request.segmentExt}"
                val targetPlaylist = File(targetDir, "index.m3u8").absolutePath
                val stdout = runFfmpegEncrypt(
                    inputM3u8 = playlistFile.absolutePath,
                    keyInfoPath = keyInfo.toAbsolutePath().toString(),
                    segmentPattern = segmentPattern,
                    targetPlaylist = targetPlaylist,
                    workDir = targetDir,
                    segmentDuration = playlistDurationSec
                )
                if (stdout.isNotBlank()) {
                    logger.debug("ffmpeg encrypt stdout ({}): {}", quality, stdout.trim())
                }
                cleanupTempKeyDir(keyInfo.parent)

                val segmentCount = countSegments(targetDir, request.segmentExt)
                variants.add(
                    VariantInfo(
                        quality = quality,
                        playlistPath = "$quality/index.m3u8",
                        segmentPrefix = "$quality/",
                        segmentCount = segmentCount
                    )
                )
            }

            val master = File(source, "master.m3u8")
            if (master.exists()) {
                val allowed = variants.map { it.playlistPath }.toSet()
                rewriteMaster(master, File(output, "master.m3u8"), allowed)
            }

            val variantsJson = JsonUtils.toJsonString(variants) ?: "[]"
            val prefix = outputContext.objectPrefix ?: throw KnownException.illegalArgument("outputDir")
            client.deleteByPrefix(prefix)
            uploadDirectory(client, output, prefix)
            val encryptedMasterPath = prefix.trimEnd('/') + "/master.m3u8"
            EncryptHlsWithQualityKeysCli.Response(
                success = true,
                encryptedMasterPath = encryptedMasterPath,
                encryptedVariants = variantsJson,
                failReason = null
            )
        }.getOrElse {
            EncryptHlsWithQualityKeysCli.Response(
                success = false,
                encryptedMasterPath = "",
                encryptedVariants = "[]",
                failReason = it.message
            )
        }.also {
            cleanupTempDir(sourceTemp)
            cleanupTempDir(outputTemp)
        }
    }

    private fun parseKeys(keysJson: String): List<KeyPayload> {
        return JsonUtils.parseArray(keysJson, KeyPayload::class.java)
    }

    private fun resolveKeyBytes(payload: KeyPayload): ByteArray {
        val hex = payload.keyPlainHex?.trim()
        if (!hex.isNullOrBlank()) {
            return hexToBytes(hex)
        }
        val base64 = payload.keyCiphertextBase64?.trim()
        if (!base64.isNullOrBlank()) {
            return Base64.getDecoder().decode(base64)
        }
        throw KnownException.illegalArgument("keysJson")
    }

    private fun prepareSourceContext(sourceDir: String, client: OssClient): StorageContext {
        val prefix = sourceDir.trim().trim('/')
        if (prefix.isBlank()) {
            throw KnownException.illegalArgument("sourceDir")
        }
        val tempDir = Files.createTempDirectory("hls-src-").toFile()
        val downloaded = downloadByPrefix(client, prefix, tempDir.toPath())
        if (downloaded == 0) {
            throw KnownException.systemError("源目录不存在: $sourceDir")
        }
        return StorageContext(tempDir, prefix, tempDir.toPath())
    }

    private fun prepareOutputContext(outputDir: String): StorageContext {
        val prefix = outputDir.trim().trim('/')
        if (prefix.isBlank()) {
            throw KnownException.illegalArgument("outputDir")
        }
        val tempDir = Files.createTempDirectory("hls-enc-").toFile()
        return StorageContext(tempDir, prefix, tempDir.toPath())
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

    private fun buildKeyInfoFile(keyBytes: ByteArray, ivHex: String, payload: KeyPayload): Path {
        val tmpDir = Files.createTempDirectory("hls-key-${UUID.randomUUID()}")
        val keyPath = tmpDir.resolve("enc.key")
        val keyInfoPath = tmpDir.resolve("key.info")
        Files.write(keyPath, keyBytes)
        val keyUri = payload.keyUriTemplate?.takeIf { it.isNotBlank() }
            ?: "/video/enc/key?keyId=${payload.keyId}&quality=${payload.quality}&token=__TOKEN__"
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

    private fun cleanupTempKeyDir(dir: Path) {
        runCatching {
            Files.list(dir).use { stream ->
                stream.forEach { Files.deleteIfExists(it) }
            }
            Files.deleteIfExists(dir)
        }
    }

    /**
     * 读取源 master，保留与已加密变体匹配的流条目，写入新的 master
     */
    private fun rewriteMaster(sourceMaster: File, targetMaster: File, allowedPlaylists: Set<String>) {
        val lines = sourceMaster.readLines()
        val builder = StringBuilder()
        builder.appendLine("#EXTM3U")
        builder.appendLine("#EXT-X-VERSION:3")
        var i = 0
        while (i < lines.size) {
            val line = lines[i]
            if (line.startsWith("#EXT-X-STREAM-INF")) {
                val next = lines.getOrNull(i + 1)
                if (next != null && allowedPlaylists.contains(next.trim())) {
                    builder.appendLine(line)
                    builder.appendLine(next.trim())
                }
                i += 2
                continue
            }
            i++
        }
        targetMaster.writeText(builder.toString())
    }

    /**
     * 从源目录中探测分片时长，默认取首个 EXTINF，失败回退 4s
     */
    private fun detectSegmentDuration(sourceDir: File): Int {
        val master = File(sourceDir, "master.m3u8")
        val variant = sourceDir.listFiles { f -> f.isDirectory }?.firstOrNull { File(it, "index.m3u8").exists() }
        val playlist = if (variant != null) File(variant, "index.m3u8") else null
        val file = when {
            playlist?.exists() == true -> playlist
            master.exists() -> master
            else -> null
        } ?: return 4
        return file.useLines { seq ->
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

    private fun cleanupTempDir(path: Path?) {
        if (path == null) return
        runCatching { path.toFile().deleteRecursively() }
    }

    data class KeyPayload(
        val quality: String = "",
        val keyId: String = "",
        val keyPlainHex: String? = null,
        val keyCiphertextBase64: String? = null,
        val ivHex: String? = null,
        val keyUriTemplate: String? = null
    )

    data class VariantInfo(
        val quality: String,
        val playlistPath: String,
        val segmentPrefix: String,
        val segmentCount: Int
    )

    private data class StorageContext(
        val localDir: File,
        val objectPrefix: String?,
        val tempPath: Path?
    )
}

