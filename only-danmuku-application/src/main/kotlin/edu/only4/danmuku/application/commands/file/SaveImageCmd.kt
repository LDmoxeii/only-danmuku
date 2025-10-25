package edu.only4.danmuku.application.commands.file

import cn.hutool.core.util.IdUtil
import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.enums.config.properties.FileAppProperties
import jakarta.validation.constraints.NotNull
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 保存图片命令
 *
 */
object SaveImageCmd {

    private val logger = LoggerFactory.getLogger(SaveImageCmd::class.java)
    private const val RANDOM_STRING_LENGTH = 30
    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")

    @Service
    class Handler(
        private val fileProps: FileAppProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 1. 构建日期目录
            val day = LocalDate.now().format(DATE_FORMATTER)
            val folder = File(fileProps.projectFolder, "${fileProps.fileFolder}/${fileProps.fileCover}$day")

            // 2. 确保目录存在
            if (!folder.exists() && !folder.mkdirs()) {
                throw KnownException.systemError("无法创建图片目录: ${folder.absolutePath}")
            }

            // 3. 生成随机文件名
            val originalFileName = request.file.originalFilename
                ?: throw KnownException.illegalArgument("文件名不能为空")

            val fileSuffix = originalFileName.substring(originalFileName.lastIndexOf("."))
            val randomFileName = IdUtil.fastSimpleUUID().substring(0, RANDOM_STRING_LENGTH) + fileSuffix

            // 4. 保存文件
            val targetFile = File(folder, randomFileName)
            try {
                request.file.transferTo(targetFile)
                logger.info("图片保存成功: {}", targetFile.absolutePath)
            } catch (e: Exception) {
                throw KnownException.systemError("图片保存失败: ${e.message}")
            }

            // 5. 生成缩略图（如果需要）
            if (request.createThumbnail) {
                try {
                    com.only.engine.misc.createImageThumbnail(targetFile.absolutePath, showLog = false)
                    logger.info("缩略图生成成功")
                } catch (e: Exception) {
                    logger.warn("缩略图生成失败: ${e.message}", e)
                    // 缩略图生成失败不影响主流程
                }
            }

            // 6. 返回相对路径
            val relativePath = "${fileProps.fileCover}$day/$randomFileName"
            return Response(filePath = relativePath)
        }
    }

    data class Request(
        @field:NotNull(message = "图片文件不能为空")
        val file: MultipartFile,

        /** 是否生成缩略图 */
        val createThumbnail: Boolean = false,
    ) : RequestParam<Response>

    data class Response(
        /** 文件相对路径 */
        val filePath: String,
    )
}
