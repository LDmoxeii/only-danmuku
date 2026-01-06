package edu.only4.danmuku.application.commands.file_upload_session

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.ValidateDeleteUploadSession
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

/**
 * 删除上传中的视频（终止并清理会话）
 */
object DeleteUploadSessionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val uploadId = request.uploadId

            val session: VideoFileUploadSession = Mediator.repositories.findOne(
                SVideoFileUploadSession.predicateById(uploadId),
                persist = false
            ).getOrNull() ?: throw KnownException("文件不存在请重新上传")

            session.ensureOwnedBy(request.customerId)

            val tempPath = session.tempDir?.trim().orEmpty()

            // 终止并软删除会话
            val now = Instant.now().epochSecond
            session.abort(now)

            Mediator.uow.remove(session)

            return Response(
                tempPath = tempPath.ifBlank { null }
            )
        }
    }

    @ValidateDeleteUploadSession
    data class Request(
        val customerId: Long,
        val uploadId: Long,
    ) : RequestParam<Response>

    data class Response(
        val tempPath: String?
    )
}

