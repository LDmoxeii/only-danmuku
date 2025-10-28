package edu.only4.danmuku.application.commands.file_upload_session

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.validater.ValidateDeleteUploadSession
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession
import org.springframework.stereotype.Service
import java.io.File
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

/**
 * 删除上传中的视频（终止并清理会话）
 */
object DeleteUploadSessionCmd {

    @Service
    class Handler(
        private val fileProps: FileAppProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val uploadId = request.uploadId

            val session: VideoFileUploadSession = Mediator.repositories.findOne(
                SVideoFileUploadSession.predicateById(uploadId),
                persist = false
            ).getOrNull() ?: throw KnownException("文件不存在请重新上传")

            session.ensureOwnedBy(request.customerId)

            // 删除临时目录（安全校验）
            val baseRoot = File(fileProps.projectFolder + Constants.FILE_FOLDER + Constants.FILE_FOLDER_TEMP)
            val sessionRel = session.tempPath ?: ""
            val targetDir = File(baseRoot, sessionRel)

            // 通过 canonicalPath 做前缀校验，避免越权删除
            val baseCanonical = baseRoot.canonicalPath + File.separator
            val targetCanonical = targetDir.canonicalPath
            if (!targetCanonical.startsWith(baseCanonical)) {
                throw KnownException("非法的删除路径")
            }
            if (targetDir.exists()) {
                targetDir.deleteRecursively()
            }

            // 终止并软删除会话
            val now = Instant.now().epochSecond
            session.abort(now)

            Mediator.uow.remove(session)
            return Response()
        }
    }

    @ValidateDeleteUploadSession
    data class Request(
        val customerId: Long,
        val uploadId: Long,
    ) : RequestParam<Response>

    class Response
}

