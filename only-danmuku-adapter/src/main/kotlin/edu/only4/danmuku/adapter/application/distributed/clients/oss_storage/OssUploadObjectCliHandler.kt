package edu.only4.danmuku.adapter.application.distributed.clients.oss_storage

import com.only.engine.exception.KnownException
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.oss_storage.OssUploadObjectCli
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files

/**
 * 上传单对象到 OSS
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class OssUploadObjectCliHandler : RequestHandler<OssUploadObjectCli.Request, OssUploadObjectCli.Response> {
    override fun exec(request: OssUploadObjectCli.Request): OssUploadObjectCli.Response {
        val objectKey = request.objectKey.trim().trimStart('/')
        if (objectKey.isBlank()) {
            throw KnownException.illegalArgument("objectKey")
        }
        val localFile = File(request.localPath)
        if (!localFile.exists() || !localFile.isFile) {
            throw KnownException("文件不存在: ${localFile.absolutePath}")
        }
        val contentType = request.contentType?.takeIf { it.isNotBlank() }
            ?: Files.probeContentType(localFile.toPath())
        val client = OssFactory.instance()
        val result = localFile.inputStream().use { input ->
            client.upload(input, objectKey, localFile.length(), contentType)
        }
        return OssUploadObjectCli.Response(
            url = result.url,
            eTag = result.eTag,
            size = localFile.length()
        )
    }
}

