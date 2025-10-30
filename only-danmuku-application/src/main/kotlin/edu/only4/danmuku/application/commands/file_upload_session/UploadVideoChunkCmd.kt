package edu.only4.danmuku.application.commands.file_upload_session

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.validator.ValidateUploadChunk
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

/**
 * 上传视频分片命令
 */
object UploadVideoChunkCmd {

    @Service
    class Handler(
        private val fileProps: FileAppProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val uploadId = request.uploadId

            // 加载会话
            val session: VideoFileUploadSession = Mediator.repositories.findFirst(
                SVideoFileUploadSession.predicateById(uploadId)
            ).getOrNull() ?: throw KnownException("文件不存在请重新上传")

            // 归属与可用性检查（聚合内部方法）
            session.ensureOwnedBy(request.customerId)
            session.ensureActive()
            session.ensureChunkAllowed(request.chunkIndex)

            // 构造分片写入路径
            val base = fileProps.projectFolder +
                    Constants.FILE_FOLDER +
                    Constants.FILE_FOLDER_TEMP +
                    (session.tempPath ?: throw KnownException("上传会话临时目录缺失"))

            val targetDir = File(base)
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }

            val targetFile = File(targetDir, request.chunkIndex.toString())
            // 写入分片（允许重传覆盖）
            request.chunkFile.transferTo(targetFile)

            // 推进聚合状态
            val now = Instant.now().epochSecond
            session.onChunkUploaded(request.chunkIndex, request.chunkFile.size, now)
            session.tryMarkDoneIfComplete()

            Mediator.uow.save()
            return Response()
        }
    }

    @ValidateUploadChunk
    data class Request(
        val customerId: Long,
        val uploadId: Long,
        val chunkIndex: Int,
        val chunkFile: MultipartFile,
    ) : RequestParam<Response>

    class Response
}

