package edu.only4.danmuku.application.commands.video_file_upload_session

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

/**
 * 初始化临时文件并标记开始上传
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/01
 */
object InitTempAndStartUploadingCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val session: VideoFileUploadSession = Mediator.repositories.findFirst(
                SVideoFileUploadSession.predicateById(request.uploadId)
            ).getOrNull() ?: return Response()

            val tempDir = request.tempDir.trim()
            if (tempDir.isBlank()) {
                throw KnownException.illegalArgument("tempPath")
            }
            val now = Instant.now().epochSecond
            session.initTempAndStartUploading(tempDir, now)

            Mediator.uow.save()

            return Response(
            )
        }

    }

    class Request(
        val uploadId: Long,
        val tempDir: String,
    ) : RequestParam<Response>

    class Response(
    )
}
