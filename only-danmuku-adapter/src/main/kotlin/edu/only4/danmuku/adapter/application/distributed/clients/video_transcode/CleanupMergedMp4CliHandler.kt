package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.video_transcode.CleanupMergedMp4Cli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

/**
 * 防腐层：删除合并生成的临时 MP4 文件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 */
@Service
class CleanupMergedMp4CliHandler : RequestHandler<CleanupMergedMp4Cli.Request, CleanupMergedMp4Cli.Response> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun exec(request: CleanupMergedMp4Cli.Request): CleanupMergedMp4Cli.Response {
        return runCatching {
            val target = File(request.mergedMp4Path)
            if (!target.exists()) {
                return CleanupMergedMp4Cli.Response(success = true, failReason = null)
            }
            if (target.isDirectory) {
                val ok = target.deleteRecursively()
                if (!ok) return CleanupMergedMp4Cli.Response(success = false, failReason = "删除目录失败: ${target.absolutePath}")
            } else {
                val ok = target.delete()
                if (!ok) return CleanupMergedMp4Cli.Response(success = false, failReason = "删除文件失败: ${target.absolutePath}")
            }
            CleanupMergedMp4Cli.Response(success = true, failReason = null)
        }.getOrElse { ex ->
            logger.error("删除临时 MP4 失败: {}", request.mergedMp4Path, ex)
            CleanupMergedMp4Cli.Response(success = false, failReason = ex.message)
        }
    }
}
