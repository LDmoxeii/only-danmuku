package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.video_transcode.CleanupTempUploadDirCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

/**
 * 防腐层：删除上传会话的临时分片目录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 */
@Service
class CleanupTempUploadDirCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<CleanupTempUploadDirCli.Request, CleanupTempUploadDirCli.Response> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: CleanupTempUploadDirCli.Request): CleanupTempUploadDirCli.Response {
        return runCatching {
            val target = resolveTempDir(request.tempPath)
            if (!target.exists()) {
                return CleanupTempUploadDirCli.Response(success = true, failReason = null)
            }
            val ok = target.deleteRecursively()
            if (!ok) {
                CleanupTempUploadDirCli.Response(success = false, failReason = "删除临时目录失败: ${target.absolutePath}")
            } else {
                CleanupTempUploadDirCli.Response(success = true, failReason = null)
            }
        }.getOrElse { ex ->
            logger.error("删除临时目录失败: {}", request.tempPath, ex)
            CleanupTempUploadDirCli.Response(success = false, failReason = ex.message)
        }
    }

    private fun resolveTempDir(tempPath: String): File {
        val f = File(tempPath)
        if (f.isAbsolute) {
            return f
        }
        return File(fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP + tempPath)
    }
}
