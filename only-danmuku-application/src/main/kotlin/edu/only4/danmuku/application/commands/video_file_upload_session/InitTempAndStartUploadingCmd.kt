package edu.only4.danmuku.application.commands.video_file_upload_session

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession
import org.springframework.stereotype.Service
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.jvm.optionals.getOrNull

/**
 * TODO： 这个应该属于防腐端而不是命令，后续调整
 * 初始化临时文件并标记开始上传
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/01
 */
object InitTempAndStartUploadingCmd {

    @Service
    class Handler(
        private val fileProps: FileAppProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val session: VideoFileUploadSession = Mediator.repositories.findFirst(
                SVideoFileUploadSession.predicateById(request.uploadId)
            ).getOrNull() ?: return Response()

            val now = Instant.now().epochSecond
            // temp/yyyyMMdd/{userId}/{uploadId}
            val day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            val relativeTempPath = "$day/${session.customerId}/${session.id}"
            val fullPath = fileProps.projectFolder +
                    Constants.FILE_FOLDER +
                    Constants.FILE_FOLDER_TEMP +
                    relativeTempPath

            File(fullPath).mkdirs()

            session.initTempAndStartUploading(relativeTempPath, now)

            Mediator.uow.save()

            return Response(
            )
        }

    }

    class Request(
        val uploadId: Long,
    ) : RequestParam<Response>

    class Response(
    )
}
