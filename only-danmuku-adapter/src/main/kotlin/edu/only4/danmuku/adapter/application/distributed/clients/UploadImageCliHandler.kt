package edu.only4.danmuku.adapter.application.distributed.clients

import cn.hutool.core.util.IdUtil
import com.only.engine.exception.KnownException
import com.only.engine.misc.createImageThumbnail
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.UploadImageCli
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDate

/**
 * 上传图片
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
@Service
class UploadImageCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<UploadImageCli.Request, String> {
    private val logger = LoggerFactory.getLogger(UploadImageCli::class.java)


    override fun exec(request: UploadImageCli.Request): String {
        val month = LocalDate.now().format(UploadImageCli.MONTH_FORMATTER)
        val folder = File(fileProps.projectFolder, "${Constants.FILE_FOLDER}${Constants.FILE_COVER}$month")

        if (!folder.exists() && !folder.mkdirs()) {
            throw KnownException.systemError("无法创建图片目录: ${folder.absolutePath}")
        }

        val originalFileName = request.file.originalFilename
            ?: throw KnownException.illegalArgument("文件名不能为空")

        val fileSuffix = originalFileName.substring(originalFileName.lastIndexOf("."))
        val randomFileName = IdUtil.fastSimpleUUID().substring(0, UploadImageCli.RANDOM_STRING_LENGTH) + fileSuffix

        val targetFile = File(folder, randomFileName)
        try {
            request.file.transferTo(targetFile)
            logger.info("图片保存成功: {}", targetFile.absolutePath)
        } catch (e: Exception) {
            throw KnownException.systemError("图片保存失败: ${e.message}")
        }

        if (request.createThumbnail) {
            try {
                createImageThumbnail(targetFile.absolutePath, showLog = fileProps.showFFmpegLog)
                logger.info("缩略图生成成功")
            } catch (e: Exception) {
                logger.warn("缩略图生成失败: ${e.message}", e)
            }
        }

        val relativePath = "${Constants.FILE_COVER}$month/$randomFileName"
        return relativePath
    }
}
