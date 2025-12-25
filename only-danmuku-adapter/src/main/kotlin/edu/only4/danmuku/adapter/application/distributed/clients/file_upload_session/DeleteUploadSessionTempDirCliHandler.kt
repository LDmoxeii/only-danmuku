package edu.only4.danmuku.adapter.application.distributed.clients.file_upload_session

import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.file_upload_session.DeleteUploadSessionTempDirCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

/**
 * 删除上传会话临时目录（仅支持绝对路径）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class DeleteUploadSessionTempDirCliHandler :
    RequestHandler<DeleteUploadSessionTempDirCli.Request, DeleteUploadSessionTempDirCli.Response> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: DeleteUploadSessionTempDirCli.Request): DeleteUploadSessionTempDirCli.Response {
        val tempPath = request.tempPath.trim()
        if (tempPath.isBlank()) {
            return DeleteUploadSessionTempDirCli.Response(success = false, failReason = "tempPath为空")
        }
        return runCatching {
            val target = File(tempPath)
            if (!target.isAbsolute) {
                return DeleteUploadSessionTempDirCli.Response(success = false, failReason = "临时目录必须为绝对路径")
            }
            if (!target.exists()) {
                return DeleteUploadSessionTempDirCli.Response(success = true, failReason = null)
            }
            val ok = target.deleteRecursively()
            if (!ok) {
                DeleteUploadSessionTempDirCli.Response(
                    success = false,
                    failReason = "删除临时目录失败: ${target.absolutePath}"
                )
            } else {
                DeleteUploadSessionTempDirCli.Response(success = true, failReason = null)
            }
        }.getOrElse { ex ->
            logger.error("删除临时目录失败: {}", tempPath, ex)
            DeleteUploadSessionTempDirCli.Response(success = false, failReason = ex.message)
        }
    }
}
