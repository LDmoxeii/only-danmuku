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
            // 发布创建事件（持久化后触发订阅者初始化临时目录）
            session.onCreate()

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
