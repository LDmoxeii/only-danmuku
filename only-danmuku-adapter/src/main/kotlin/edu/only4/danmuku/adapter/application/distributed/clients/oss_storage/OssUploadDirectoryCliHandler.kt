package edu.only4.danmuku.adapter.application.distributed.clients.oss_storage

import com.only.engine.exception.KnownException
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.oss_storage.OssUploadDirectoryCli
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

/**
 * 上传目录到 OSS（用于 HLS 输出）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class OssUploadDirectoryCliHandler : RequestHandler<OssUploadDirectoryCli.Request, OssUploadDirectoryCli.Response> {
    override fun exec(request: OssUploadDirectoryCli.Request): OssUploadDirectoryCli.Response {
        val baseDir = File(request.localDir)
        if (!baseDir.exists() || !baseDir.isDirectory) {
            throw KnownException("目录不存在: ${baseDir.absolutePath}")
        }
        val prefix = request.objectPrefix.trim().trim('/')
        if (prefix.isBlank()) {
            throw KnownException.illegalArgument("objectPrefix")
        }
        val matcher = request.includeGlob?.takeIf { it.isNotBlank() }?.let {
            FileSystems.getDefault().getPathMatcher("glob:$it")
        }
        val client = OssFactory.instance()
        val basePath = baseDir.toPath()
        var count = 0
        Files.walk(basePath).use { paths ->
            paths.filter { Files.isRegularFile(it) }.forEach { path ->
                if (matcher != null && !matcher.matches(relativizePath(basePath, path))) {
                    return@forEach
                }
                val relative = basePath.relativize(path).toString().replace('\\', '/')
                val objectKey = "$prefix/$relative".replace("//", "/")
                val contentType = Files.probeContentType(path)
                path.toFile().inputStream().use { input ->
                    client.upload(input, objectKey, Files.size(path), contentType)
                }
                count++
            }
        }
        return OssUploadDirectoryCli.Response(
            uploadedCount = count,
            prefix = prefix
        )
    }

    private fun relativizePath(base: Path, path: Path): Path {
        return runCatching { base.relativize(path) }.getOrDefault(path.fileName)
    }
}

