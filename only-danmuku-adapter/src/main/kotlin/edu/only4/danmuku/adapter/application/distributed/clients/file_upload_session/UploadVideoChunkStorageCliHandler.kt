package edu.only4.danmuku.adapter.application.distributed.clients.file_upload_session

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.file_upload_session.UploadVideoChunkStorageCli

import org.springframework.stereotype.Service
import java.io.File

/**
 * 保存视频上传分片到临时存储（当前本地实现，可扩展为 OSS）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class UploadVideoChunkStorageCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<UploadVideoChunkStorageCli.Request, UploadVideoChunkStorageCli.Response> {
    override fun exec(request: UploadVideoChunkStorageCli.Request): UploadVideoChunkStorageCli.Response {
        val tempPath = request.tempPath.trim()
        if (tempPath.isBlank()) {
            throw KnownException.illegalArgument("tempPath")
        }
        if (request.chunkIndex < 0) {
            throw KnownException.illegalArgument("chunkIndex")
        }
        val baseRoot = File(fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP)
        val targetDir = File(baseRoot, tempPath)
        val baseCanonical = baseRoot.canonicalPath + File.separator
        val targetCanonical = targetDir.canonicalPath
        if (!targetCanonical.startsWith(baseCanonical)) {
            throw KnownException("非法的存储路径")
        }
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }
        val targetFile = File(targetDir, request.chunkIndex.toString())
        request.chunkFile.transferTo(targetFile)

        val normalizedTemp = tempPath.trim('/','\\').replace('\\', '/')
        val storedPath = "$normalizedTemp/${request.chunkIndex}"
        return UploadVideoChunkStorageCli.Response(
            storedPath = storedPath,
            size = targetFile.length()
        )
    }
}

