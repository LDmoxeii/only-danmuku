package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only.engine.error.CommonErrors
import com.only.engine.exception.AppException
import com.only.engine.exception.BusinessException
import com.only.engine.exception.DependencyException
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import edu.only4.danmuku.domain.shared.error.DanmukuBusinessErrors
import com.only.engine.json.misc.JsonUtils
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.video_encrypt.GenerateEncryptedMasterByVariantsCli
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path

/**
 * 防腐层：按档位列表生成加密 master.m3u8
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Service
class GenerateEncryptedMasterByVariantsCliHandler : RequestHandler<GenerateEncryptedMasterByVariantsCli.Request, GenerateEncryptedMasterByVariantsCli.Response> {
    override fun exec(request: GenerateEncryptedMasterByVariantsCli.Request): GenerateEncryptedMasterByVariantsCli.Response {
        var tempDir: Path? = null
        return runCatching {
            val variants = parseVariants(request.variantsJson)
            if (variants.isEmpty()) {
                throw RequestException(CommonErrors.PARAM_INVALID, "variantsJson")
            }
            val outputPrefix = normalizePrefix(request.outputDir, "outputDir")
            val sorted = variants.sortedWith(
                compareByDescending<VariantPayload> { it.bandwidthBps }
                    .thenBy { it.quality }
            )
            val masterContent = buildMasterContent(sorted)

            tempDir = Files.createTempDirectory("hls-master-")
            val masterFile = tempDir!!.resolve("master.m3u8").toFile()
            masterFile.writeText(masterContent)

            val client = OssFactory.instance()
            val objectKey = "${outputPrefix.trimEnd('/')}/master.m3u8"
            val contentType = Files.probeContentType(masterFile.toPath()) ?: "application/vnd.apple.mpegurl"
            masterFile.inputStream().use { input ->
                client.upload(input, objectKey, masterFile.length(), contentType)
            }

            GenerateEncryptedMasterByVariantsCli.Response(
                success = true,
                masterPath = objectKey,
                failReason = null
            )
        }.getOrElse { ex ->
            GenerateEncryptedMasterByVariantsCli.Response(
                success = false,
                masterPath = null,
                failReason = ex.message
            )
        }.also {
            cleanupTempDir(tempDir)
        }
    }

    private fun parseVariants(json: String): List<VariantPayload> {
        return JsonUtils.parseArray(json, VariantPayload::class.java)
    }

    private fun normalizePrefix(prefix: String, field: String): String {
        val normalized = prefix.trim().trim('/')
        if (normalized.isBlank()) {
            throw RequestException(CommonErrors.PARAM_INVALID, field)
        }
        return normalized
    }

    private fun buildMasterContent(variants: List<VariantPayload>): String {
        val builder = StringBuilder()
        builder.appendLine("#EXTM3U")
        builder.appendLine("#EXT-X-VERSION:3")
        variants.forEach { variant ->
            val playlistPath = variant.playlistPath.trim()
            if (playlistPath.isBlank()) {
                throw RequestException(CommonErrors.PARAM_INVALID, "variantsJson")
            }
            builder.appendLine(
                "#EXT-X-STREAM-INF:BANDWIDTH=${variant.bandwidthBps},RESOLUTION=${variant.width}x${variant.height}"
            )
            builder.appendLine(playlistPath)
        }
        return builder.toString()
    }

    private fun cleanupTempDir(path: Path?) {
        if (path == null) return
        runCatching { path.toFile().deleteRecursively() }
    }

    data class VariantPayload(
        val quality: String = "",
        val width: Int = 0,
        val height: Int = 0,
        val bandwidthBps: Int = 0,
        val playlistPath: String = "",
    )
}

