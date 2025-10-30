package edu.only4.danmuku.application.commands.file

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import kotlin.jvm.optionals.getOrNull

/**
 * 清理视频草稿相关的临时文件
 * 临时目录由 CreateUploadSessionCmd 创建：temp/yyyyMMdd/{userId}/{uploadId}
 * 这里根据视频下的各个文件对应的 uploadId 定位并删除对应 temp 目录。
 */
object CleanTempFilesCmd {

    private val logger = LoggerFactory.getLogger(CleanTempFilesCmd::class.java)

    @Service
    class Handler(
        private val fileProps: FileAppProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val videoPost = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoId),
                persist = false
            ).getOrNull() ?: throw KnownException("视频不存在或已删除")

            val baseRoot = File(fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP)
            val baseCanonical = baseRoot.canonicalPath + File.separator

            var anyDeleted = false

            // 批量收集 uploadId，一次性查询
            val uploadIds = videoPost.videoFilePosts.map { it.uploadId }.distinct()
            if (uploadIds.isEmpty()) return Response(cleaned = false)

            val sessions = Mediator.repositories.find(
                SVideoFileUploadSession.predicateByIds(uploadIds),
                persist = false
            )
            val idToTemp = sessions.associate { it.id to (it.tempPath ?: "") }

            uploadIds.forEach { uploadId ->
                val tempRel = idToTemp[uploadId]
                if (tempRel.isNullOrBlank()) return@forEach

                val targetDir = File(baseRoot, tempRel)
                val targetCanonical = runCatching { targetDir.canonicalPath }.getOrNull()
                if (targetCanonical == null || !targetCanonical.startsWith(baseCanonical)) {
                    logger.warn("拒绝清理越权路径: {}", targetDir.absolutePath)
                    return@forEach
                }

                if (targetDir.exists()) {
                    runCatching { targetDir.deleteRecursively() }
                        .onSuccess {
                            anyDeleted = true
                            logger.info(
                                "已清理上传会话临时目录: customerId={}, videoId={}, uploadId={}, path={}",
                                request.customerId, request.videoId, uploadId, targetDir.absolutePath
                            )
                        }
                        .onFailure { e ->
                            logger.warn(
                                "清理上传会话临时目录失败: customerId={}, videoId={}, uploadId={}, msg={}",
                                request.customerId, request.videoId, uploadId, e.message
                            )
                        }
                }
            }

            return Response(cleaned = anyDeleted)
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
