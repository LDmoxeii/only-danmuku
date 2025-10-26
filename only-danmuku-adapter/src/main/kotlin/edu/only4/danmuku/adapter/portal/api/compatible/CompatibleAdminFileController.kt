package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileGetResource
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileGetVideoResource
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileGetVideoResourceTs
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileUploadImage
import edu.only4.danmuku.application.commands.file.SaveImageCmd
import edu.only4.danmuku.application.queries.file.GetFileResourceQry
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

    @RequestMapping("/uploadImage")
    fun adminFileUploadImage(
        @NotNull file: MultipartFile,
        @NotNull createThumbnail: Boolean,
    ): String {
        val result = Mediator.commands.send(
            SaveImageCmd.Request(
                file = file,
                createThumbnail = createThumbnail
            )
        )
        return result.filePath
    }

    @SaIgnore
    @GetMapping("/getResource")
    fun adminFileGetResource(
        @RequestParam sourceName: String,
        response: HttpServletResponse,
    ) {
        // 使用查询处理器获取文件信息
        val result = Mediator.queries.send(
            GetFileResourceQry.Request(sourceName = sourceName)
        )

        // 如果文件不存在，直接返回
        if (!result.exists) {
            response.status = HttpServletResponse.SC_NOT_FOUND
            return
        }

        // 设置响应内容类型
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

        // 设置缓存控制头（30天）
        response.setHeader("Cache-Control", "max-age=2592000")

        // 读取文件并写入响应流
        readFile(response, result.filePath)
    }

    /**
     * 读取文件并写入响应流
     */
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

    /**
     * 获取视频资源(m3u8)
     */
    @GetMapping("/videoResource/{fileId}")
    fun adminFileGetVideoResource(@PathVariable fileId: String = ""): AdminFileGetVideoResource.Response {
        // TODO: 实现获取视频资源(m3u8)逻辑
        return AdminFileGetVideoResource.Response()
    }

    /**
     * 获取视频TS分片
     */
    @GetMapping("/videoResource/{fileId}/{ts}")
    fun adminFileGetVideoResourceTs(
        @PathVariable fileId: String = "",
        @PathVariable ts: String = "",
    ): AdminFileGetVideoResourceTs.Response {
        // TODO: 实现获取视频TS分片逻辑
        return AdminFileGetVideoResourceTs.Response()
    }

}
