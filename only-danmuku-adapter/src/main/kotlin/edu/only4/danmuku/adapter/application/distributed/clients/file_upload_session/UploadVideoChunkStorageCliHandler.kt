package edu.only4.danmuku.adapter.application.distributed.clients.file_upload_session

import com.only.engine.error.CommonErrors
import com.only.engine.exception.AppException
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import com.only4.cap4k.ddd.core.application.RequestHandler

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
class UploadVideoChunkStorageCliHandler :
    RequestHandler<UploadVideoChunkStorageCli.Request, UploadVideoChunkStorageCli.Response> {
    override fun exec(request: UploadVideoChunkStorageCli.Request): UploadVideoChunkStorageCli.Response {
        val tempPath = request.tempPath.trim()
        if (tempPath.isBlank()) {
            throw RequestException(CommonErrors.PARAM_INVALID, "tempPath")
        }
        if (request.chunkIndex < 0) {
            throw RequestException(CommonErrors.PARAM_INVALID, "chunkIndex")
        }
        try {
            val targetDir = resolveTempDir(tempPath)
            if (!targetDir.exists() && !targetDir.mkdirs()) {
                throw SystemException(CommonErrors.SYSTEM_ERROR, "创建临时目录失败: ${targetDir.absolutePath}")
            }
            val targetFile = File(targetDir, request.chunkIndex.toString())
            request.chunkFile.transferTo(targetFile)

            val storedPath = targetFile.absolutePath.replace('\\', '/')
            return UploadVideoChunkStorageCli.Response(
                storedPath = storedPath,
                size = targetFile.length()
            )
        } catch (e: AppException) {
            throw e
        } catch (e: Exception) {
            throw SystemException(CommonErrors.SYSTEM_ERROR, "分片存储失败", cause = e)
        }
    }

    private fun resolveTempDir(tempPath: String): File {
        val target = File(tempPath)
        if (target.isAbsolute) {
            return target
        }
        throw RequestException(CommonErrors.PARAM_INVALID, "tempPath")
    }
}

