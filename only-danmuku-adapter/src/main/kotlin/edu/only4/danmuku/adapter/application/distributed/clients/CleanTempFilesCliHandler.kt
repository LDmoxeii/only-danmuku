package edu.only4.danmuku.adapter.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.CleanTempFilesCli
import org.springframework.stereotype.Service
import java.io.File

/**
 * 清理视频草稿相关的临时文件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
@Service
class CleanTempFilesCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<CleanTempFilesCli.Request, CleanTempFilesCli.Response> {
    override fun exec(request: CleanTempFilesCli.Request): CleanTempFilesCli.Response {
        val baseRoot = File(fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP)
        val baseCanonical = baseRoot.canonicalPath + File.separator

        var anyDeleted = false

        request.tempPaths.forEach { tempRel ->
            val targetDir = File(baseRoot, tempRel)
            val targetCanonical = runCatching { targetDir.canonicalPath }.getOrNull()
            if (targetCanonical == null || !targetCanonical.startsWith(baseCanonical)) {
                return@forEach
            }

            if (targetDir.exists()) {
                runCatching { targetDir.deleteRecursively() }
                    .onSuccess {
                        anyDeleted = true
                    }
            }
        }

        return CleanTempFilesCli.Response(cleaned = anyDeleted)
    }
}
