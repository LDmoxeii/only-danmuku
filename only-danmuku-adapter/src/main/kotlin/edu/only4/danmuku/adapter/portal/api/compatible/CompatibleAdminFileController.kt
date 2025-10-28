package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.file.UploadImageCmd
import edu.only4.danmuku.application.queries.file.GetFileResourceQry
import edu.only4.danmuku.application.queries.file.GetVideoResourceQry
import edu.only4.danmuku.application.queries.file.GetVideoResourceTsQry
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.annotations.NotNull
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream

/**
 * 管理员文件管理控制器
 */
@RestController
@RequestMapping("/admin/file")
@Validated
class CompatibleAdminFileController {

    private val logger = LoggerFactory.getLogger(CompatibleAdminFileController::class.java)

    @PostMapping("/uploadImage")
    fun uploadImage(
        @NotNull file: MultipartFile,
        @NotNull createThumbnail: Boolean,
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
    @PostMapping("/getResource")
    fun getResource(
        @RequestParam sourceName: String,
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
