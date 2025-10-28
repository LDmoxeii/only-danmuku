package edu.only4.danmuku.application.commands.file_upload_session

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.factory.VideoFileUploadSessionFactory
import jakarta.validation.constraints.Positive
import org.springframework.stereotype.Service
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 创建视频分片上传会话（预上传）
 */
object CreateUploadSessionCmd {

    @Service
    class Handler(
        private val fileProps: FileAppProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val now = Instant.now().epochSecond
            val expiresAt = now + 24 * 60 * 60 // 24 hours

            val session = Mediator.factories.create(
                VideoFileUploadSessionFactory.Payload(
                    customerId = request.customerId,
                    fileName = request.fileName,
                    chunks = request.chunks,
                    createTime = now,
                    expiresAt = expiresAt,
                )
            )

            // 规范化临时目录： temp/yyyyMMdd/{userId}/{uploadId}
            val day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            val relativeTempPath = "$day/${request.customerId}/${session.id}"
            val fullPath = fileProps.projectFolder +
                    Constants.FILE_FOLDER +
                    Constants.FILE_FOLDER_TEMP +
                    relativeTempPath

            File(fullPath).mkdirs()

            // 语义化方法：初始化临时目录并进入上传中状态
            session.initTempAndStartUploading(relativeTempPath, now)

            Mediator.uow.save()

            return Response(uploadId = session.id)
        }
    }

    data class Request(
        val customerId: Long,
        val fileName: String,
        @param:Positive
        val chunks: Int,
    ) : RequestParam<Response>

    data class Response(
        val uploadId: Long,
    )
}
