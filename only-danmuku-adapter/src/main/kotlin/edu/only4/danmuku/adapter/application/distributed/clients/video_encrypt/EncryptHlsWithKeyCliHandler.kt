package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestHandler
import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithKeyCli
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

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
            source.listFiles { file -> file.isDirectory }?.forEach { dir ->
                if (dir.name == "enc") return@forEach
                if (!request.quality.isNullOrBlank() && dir.name != request.quality) return@forEach
                val playlistFile = File(dir, "index.m3u8")
                if (!playlistFile.exists()) return@forEach

                val targetDir = File(output, dir.name)
                targetDir.mkdirs()
                val keyUri =
                    "/video/enc/key?keyId=${request.keyId}&token=__TOKEN__"
                rewritePlaylistWithKey(playlistFile, File(targetDir, "index.m3u8"), keyUri, request.ivHex)

                val segmentCount = copySegments(dir, targetDir, request.segmentExt)
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

    private fun rewritePlaylistWithKey(source: File, target: File, keyUri: String, ivHex: String?) {
        val lines = source.readLines()
        val builder = StringBuilder()
        builder.appendLine("#EXTM3U")
        if (lines.none { it.startsWith("#EXT-X-VERSION") }) {
            builder.appendLine("#EXT-X-VERSION:3")
        }
        val ivPart = ivHex?.let { ",IV=0x$it" } ?: ""
        builder.appendLine("#EXT-X-KEY:METHOD=AES-128,URI=\"$keyUri\"$ivPart")
        lines.filterNot { it.startsWith("#EXTM3U") || it.startsWith("#EXT-X-VERSION") || it.startsWith("#EXT-X-KEY") }
            .forEach { builder.appendLine(it) }
        target.writeText(builder.toString())
    }

    private fun copySegments(sourceDir: File, targetDir: File, segmentExt: String): Int {
        var count = 0
        sourceDir.listFiles { f -> f.isFile && f.extension == "ts" }?.forEach { seg ->
            val targetName = seg.nameWithoutExtension + segmentExt
            Files.copy(seg.toPath(), File(targetDir, targetName).toPath(), StandardCopyOption.REPLACE_EXISTING)
            count++
        }
        return count
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
}

