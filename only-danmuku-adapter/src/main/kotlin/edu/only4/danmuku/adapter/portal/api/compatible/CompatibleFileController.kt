package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.FileUploadVideo
import edu.only4.danmuku.application.commands.file_upload_session.CreateUploadSessionCmd
import edu.only4.danmuku.application.commands.file_upload_session.DeleteUploadSessionCmd
import edu.only4.danmuku.application.commands.file_upload_session.UploadVideoChunkCmd
import edu.only4.danmuku.application.commands.video.AddPlayCountCmd
import edu.only4.danmuku.application.commands.video_play_history.AddPlayHistoryCmd
import edu.only4.danmuku.application.distributed.clients.UploadImageCli
import edu.only4.danmuku.application.queries.file.GetFileResourceQry
import edu.only4.danmuku.application.queries.file.GetVideoResourceQry
import edu.only4.danmuku.application.queries.file.GetVideoResourceTsQry
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotEmpty
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream

/**
 * 文件操作控制器 - 处理文件上传、资源获取等操作
 */
@RestController
@RequestMapping("/file")
@Validated
class CompatibleFileController {

    private val logger = LoggerFactory.getLogger(CompatibleAdminFileController::class.java)

    @SaIgnore
    @GetMapping("/getResource")
    fun getResource(
        @NotEmpty sourceName: String,
        response: HttpServletResponse,
    ) {
        val result = Mediator.queries.send(
            GetFileResourceQry.Request(sourceName = sourceName)
        )

        if (!result.exists) {
            response.status = HttpServletResponse.SC_NOT_FOUND
            return
        }

        val contentType = when (result.fileSuffix.lowercase()) {
            ".jpg", ".jpeg" -> "image/jpeg"
            ".png" -> "image/png"
            ".gif" -> "image/gif"
            ".webp" -> "image/webp"
            ".bmp" -> "image/bmp"
            ".svg" -> "image/svg+xml"
            else -> "application/octet-stream"
        }
        response.contentType = contentType

        response.setHeader("Cache-Control", "max-age=2592000")

        readFile(response, result.filePath)
    }

    /**
     * 预上传视频
     */
    @PostMapping("/preUploadVideo")
    fun filePreUploadVideo(
        @NotEmpty fileName: String,
        chunks: Int,
    ): Long {
        val currentUserId = LoginHelper.getUserId()!!
        val result = Mediator.commands.send(
            CreateUploadSessionCmd.Request(
                customerId = currentUserId,
                fileName = fileName,
                chunks = chunks
            )
        )
        return result.uploadId
    }

    /**
     * 上传视频分片
     */
    @PostMapping("/uploadVideo")
    fun fileUploadVideo(
        chunkFile: MultipartFile,
        chunkIndex: Int,
        uploadId: Long,
    ): FileUploadVideo.Response {
        val currentUserId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            UploadVideoChunkCmd.Request(
                customerId = currentUserId,
                uploadId = uploadId,
                chunkIndex = chunkIndex,
                chunkFile = chunkFile
            )
        )
        return FileUploadVideo.Response()
    }

    /**
     * 删除上传中的视频
     */
    @PostMapping("/delUploadVideo")
    fun fileDelUploadVideo(
        uploadId: Long,
    ) {
        val currentUserId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            DeleteUploadSessionCmd.Request(
                customerId = currentUserId,
                uploadId = uploadId
            )
        )
    }

    @PostMapping("/uploadImage")
    fun uploadImage(
        file: MultipartFile,
        createThumbnail: Boolean,
    ): String {
        val filePath = Mediator.requests.send(
            UploadImageCli.Request(
                file = file,
                createThumbnail = createThumbnail
            )
        )
        return filePath
    }

    private fun readFile(response: HttpServletResponse, filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            return
        }

        try {
            response.outputStream.use { out ->
                FileInputStream(file).use { inputStream ->
                    val buffer = ByteArray(1024)
                    var len: Int
                    while (inputStream.read(buffer).also { len = it } != -1) {
                        out.write(buffer, 0, len)
                    }
                    out.flush()
                }
            }
        } catch (e: Exception) {
            logger.error("读取文件异常: $filePath", e)
        }
    }

}
