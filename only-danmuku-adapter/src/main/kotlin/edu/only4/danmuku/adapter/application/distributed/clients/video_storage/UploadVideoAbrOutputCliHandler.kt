package edu.only4.danmuku.adapter.application.distributed.clients.video_storage

import com.only.engine.exception.KnownException
import com.only.engine.oss.core.OssClient
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.video_storage.UploadVideoAbrOutputCli
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.util.UUID

/**
 * 上传 ABR 输出目录到 OSS（含 master/variants/segments）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class UploadVideoAbrOutputCliHandler : RequestHandler<UploadVideoAbrOutputCli.Request, UploadVideoAbrOutputCli.Response> {
    override fun exec(request: UploadVideoAbrOutputCli.Request): UploadVideoAbrOutputCli.Response {
        val outputDir = File(request.localOutputDir)
        if (!outputDir.exists() || !outputDir.isDirectory) {
            return UploadVideoAbrOutputCli.Response(
                success = false,
                storagePrefix = "",
                encPrefix = null,
                failReason = "输出目录不存在: ${outputDir.absolutePath}"
            )
        }
        val storagePrefix = resolvePrefix(request.objectPrefix)
        val client = OssFactory.instance()
        val encDir = request.encOutputDir?.takeIf { it.isNotBlank() }?.let { File(it) }
        val encDirPath = encDir?.canonicalFile?.toPath()

        return runCatching {
            uploadDirectory(client, outputDir, storagePrefix, encDirPath)
            val encPrefix = if (encDir != null && encDir.exists() && encDir.isDirectory) {
                val prefix = storagePrefix.trimEnd('/') + "/enc"
                uploadDirectory(client, encDir, prefix, null)
                prefix
            } else {
                null
            }
            UploadVideoAbrOutputCli.Response(
                success = true,
                storagePrefix = storagePrefix,
                encPrefix = encPrefix,
                failReason = null
            )
        }.getOrElse { ex ->
            UploadVideoAbrOutputCli.Response(
                success = false,
                storagePrefix = storagePrefix,
                encPrefix = null,
                failReason = ex.message
            )
        }
    }

    private fun resolvePrefix(prefix: String?): String {
        val normalized = prefix?.trim()?.trim('/') ?: ""
        if (normalized.isNotBlank()) {
            return normalized
        }
        val today = LocalDate.now()
        val uuid = UUID.randomUUID().toString().replace("-", "")
        return "video/${today.year}/${"%02d".format(today.monthValue)}/${"%02d".format(today.dayOfMonth)}/$uuid"
    }

    private fun uploadDirectory(
        client: OssClient,
        baseDir: File,
        prefix: String,
        excludeDir: java.nio.file.Path?
    ): Int {
        if (!baseDir.exists() || !baseDir.isDirectory) {
            throw KnownException("目录不存在: ${baseDir.absolutePath}")
        }
        val basePath = baseDir.toPath()
        var count = 0
        Files.walk(basePath).use { paths ->
            paths.filter { Files.isRegularFile(it) }.forEach { path ->
                if (excludeDir != null && path.startsWith(excludeDir)) {
                    return@forEach
                }
                val relative = basePath.relativize(path).toString().replace('\\', '/')
                val objectKey = "${prefix.trimEnd('/')}/$relative"
                val contentType = Files.probeContentType(path)
                path.toFile().inputStream().use { input ->
                    client.upload(input, objectKey, Files.size(path), contentType)
                }
                count++
            }
        }
        return count
    }
}

