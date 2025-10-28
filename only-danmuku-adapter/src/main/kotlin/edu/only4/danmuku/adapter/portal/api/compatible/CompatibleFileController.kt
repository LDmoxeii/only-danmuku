package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.FileDelUploadVideo
import edu.only4.danmuku.adapter.portal.api.payload.FilePreUploadVideo
import edu.only4.danmuku.adapter.portal.api.payload.FileUploadVideo
import edu.only4.danmuku.application.commands.file.UploadImageCmd
import edu.only4.danmuku.application.commands.video.AddPlayCountCmd
import edu.only4.danmuku.application.commands.video_play_history.AddPlayHistoryCmd
import edu.only4.danmuku.application.queries.file.GetFileResourceQry
import edu.only4.danmuku.application.queries.file.GetVideoResourceQry
import edu.only4.danmuku.application.queries.file.GetVideoResourceTsQry
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
    @PostMapping("/getResource")
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
    fun filePreUploadVideo(@RequestBody @Validated request: FilePreUploadVideo.Request): FilePreUploadVideo.Response {
        // TODO: 实现预上传视频逻辑
        return FilePreUploadVideo.Response()
    }

    /**
     * 上传视频分片
     */
    @PostMapping("/uploadVideo")
    fun fileUploadVideo(@RequestBody @Validated request: FileUploadVideo.Request): FileUploadVideo.Response {
        // TODO: 实现上传视频分片逻辑
        return FileUploadVideo.Response()
    }

    /**
     * 删除上传中的视频
     */
    @PostMapping("/delUploadVideo")
    fun fileDelUploadVideo(@RequestBody @Validated request: FileDelUploadVideo.Request): FileDelUploadVideo.Response {
        // TODO: 实现删除上传中的视频逻辑
        return FileDelUploadVideo.Response()
    }

    @PostMapping("/uploadImage")
    fun uploadImage(
        file: MultipartFile,
        createThumbnail: Boolean,
    ): String {
        val result = Mediator.commands.send(
            UploadImageCmd.Request(
                file = file,
                createThumbnail = createThumbnail
            )
        )
        return result.filePath
    }

    @SaIgnore
    @GetMapping("/videoResource/{fileId}")
    fun getVideoResource(
        @PathVariable fileId: String,
        response: HttpServletResponse,
    ) {
        val result = Mediator.queries.send(
            GetVideoResourceQry.Request(fileId = fileId)
        )

        if (!result.exists) {
            response.status = HttpServletResponse.SC_NOT_FOUND
            return
        }

        response.contentType = "application/vnd.apple.mpegurl"

        readFile(response, result.filePath)

        Mediator.commands.send(
            AddPlayCountCmd.Request(videoId = result.videoId)
        )

        val currentUserId = LoginHelper.getUserId() ?: return

        Mediator.commands.send(
            AddPlayHistoryCmd.Request(
                customerId = currentUserId,
                videoId = result.videoId,
                fileIndex = result.fileIndex
            )
        )
    }

    @GetMapping("/videoResource/{fileId}/{ts}")
    fun getVideoResourceTs(
        @PathVariable fileId: String,
        @PathVariable ts: String,
        response: HttpServletResponse,
    ) {
        val result = Mediator.queries.send(
            GetVideoResourceTsQry.Request(
                fileId = fileId,
                ts = ts
            )
        )

        if (!result.exists) {
            response.status = HttpServletResponse.SC_NOT_FOUND
            return
        }

        response.contentType = "video/mp2t"

        readFile(response, result.filePath)
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
