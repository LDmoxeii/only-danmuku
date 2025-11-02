package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.time.format.DateTimeFormatter

/**
 * 上传图片
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
object UploadImageCli {

    const val RANDOM_STRING_LENGTH = 30
    val MONTH_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM")

    class Request(
        val file: MultipartFile,
        val createThumbnail: Boolean = false,
    ) : RequestParam<String>
}
