package edu.only4.danmuku.adapter.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestHandler
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
class CleanTempFilesCliHandler : RequestHandler<CleanTempFilesCli.Request, Unit> {
    override fun exec(request: CleanTempFilesCli.Request) {
        request.tempPaths.forEach { tempPath ->
            val direct = File(tempPath)
            if (!direct.isAbsolute) {
                return@forEach
            }

            if (direct.exists()) {
                runCatching { direct.deleteRecursively() }
            }
        }
    }
}
