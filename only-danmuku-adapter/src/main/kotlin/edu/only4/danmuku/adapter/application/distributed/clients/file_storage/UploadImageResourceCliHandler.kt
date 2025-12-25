package edu.only4.danmuku.adapter.application.distributed.clients.file_storage

import cn.hutool.core.util.IdUtil
import com.only.engine.exception.KnownException
import com.only.engine.misc.createImageThumbnail
import com.only.engine.oss.enums.AccessPolicyType
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.file_storage.UploadImageResourceCli
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

/**
 * 上传图片到 OSS 并返回资源 key
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class UploadImageResourceCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<UploadImageResourceCli.Request, UploadImageResourceCli.Response> {
    override fun exec(request: UploadImageResourceCli.Request): UploadImageResourceCli.Response {
        val originalName = request.file.originalFilename
            ?: throw KnownException.illegalArgument("file")
        val suffix = resolveSuffix(originalName)
        val prefix = resolveBizPrefix(request.bizType)
        val month = LocalDate.now().format(UploadImageResourceCli.MONTH_FORMATTER)
        val randomName = IdUtil.fastSimpleUUID().substring(0, UploadImageResourceCli.RANDOM_STRING_LENGTH)
        val fileName = if (suffix.isBlank()) randomName else "$randomName.$suffix"
        val objectDir = "$prefix$month/"
        val resourceKey = objectDir + fileName
        val tempFile = Files.createTempFile("image-upload-", if (suffix.isBlank()) ".tmp" else ".$suffix").toFile()

        val client = OssFactory.instance()
        var thumbnailKey: String? = null
        var publicUrl: String?

        try {
            request.file.transferTo(tempFile)
            val contentType = request.file.contentType?.takeIf { it.isNotBlank() }
                ?: Files.probeContentType(tempFile.toPath())
            val result = tempFile.inputStream().use { input ->
                client.upload(input, resourceKey, tempFile.length(), contentType)
            }
            publicUrl = if (client.getAccessPolicy() == AccessPolicyType.PUBLIC) result.url else null

            if (request.createThumbnail) {
                runCatching { createImageThumbnail(tempFile.absolutePath, showLog = fileProps.showFFmpegLog) }
                    .onSuccess {
                        val thumbFile = File(tempFile.parentFile, tempFile.nameWithoutExtension + THUMBNAIL_SUFFIX)
                        if (thumbFile.exists()) {
                            val thumbName = tempFile.nameWithoutExtension + THUMBNAIL_SUFFIX
                            val thumbKey = objectDir + thumbName
                            thumbFile.inputStream().use { input ->
                                client.upload(input, thumbKey, thumbFile.length(), "image/jpeg")
                            }
                            thumbnailKey = thumbKey
                            Files.deleteIfExists(thumbFile.toPath())
                        }
                    }
            }
        } finally {
            Files.deleteIfExists(tempFile.toPath())
        }

        return UploadImageResourceCli.Response(
            resourceKey = resourceKey,
            thumbnailKey = thumbnailKey,
            publicUrl = publicUrl
        )
    }

    private fun resolveSuffix(originalName: String): String {
        val trimmed = originalName.trim()
        val dotIndex = trimmed.lastIndexOf('.')
        return if (dotIndex >= 0 && dotIndex < trimmed.length - 1) {
            trimmed.substring(dotIndex + 1)
        } else {
            ""
        }
    }

    private fun resolveBizPrefix(bizType: String): String {
        return when (bizType.uppercase()) {
            "AVATAR" -> "avatar/"
            "CATEGORY" -> "category/"
            "COMMON" -> "common/"
            "COVER" -> Constants.FILE_COVER
            else -> Constants.FILE_COVER
        }
    }

    companion object {
        private const val THUMBNAIL_SUFFIX = "_thumbnail.jpg"
    }
}

