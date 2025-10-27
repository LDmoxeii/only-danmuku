package edu.only4.danmuku.application.commands.file

import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 清理视频草稿产生的临时文件
 */
object CleanTempFilesCmd {

    private val logger = LoggerFactory.getLogger(CleanTempFilesCmd::class.java)

    @Service
    class Handler(
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            TODO("调整路径相关")
//            val baseDir = File(videoProps.tempFolder)
//            val targetDir = File(baseDir, "/")
//            val existed = targetDir.exists()
//            if (existed) {
//                targetDir.deleteRecursively()
//                logger.info("已删除视频临时目录: {}", targetDir.absolutePath)
//            } else {
//                logger.debug("临时目录不存在，跳过清理: {}", targetDir.absolutePath)
//            }
//            return Response(cleaned = existed)
        }
    }

    data class Request(
        val customerId: Long,
        val videoId: Long,
    ) : RequestParam<Response>

    data class Response(
        val cleaned: Boolean,
    )
}
