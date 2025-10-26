package edu.only4.danmuku.adapter.application.queries.file

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application.queries.file.GetFileResourceQry
import org.springframework.stereotype.Service
import java.io.File

/**
 * 获取文件资源查询处理器
 *
 * 负责验证文件路径安全性并返回文件信息
 */
@Service
class GetFileResourceQryHandler(
    private val fileProps: FileAppProperties,
) : Query<GetFileResourceQry.Request, GetFileResourceQry.Response> {

    companion object {
        private const val FILE_FOLDER = "file/"
    }

    override fun exec(request: GetFileResourceQry.Request): GetFileResourceQry.Response {
        val sourceName = request.sourceName

        // 获取文件后缀
        val fileSuffix = getFileSuffix(sourceName)

        // 构造完整文件路径
        val fullPath = fileProps.projectFolder + FILE_FOLDER + sourceName
        val file = File(fullPath)

        return GetFileResourceQry.Response(
            filePath = fullPath,
            fileSuffix = fileSuffix,
            exists = file.exists() && file.isFile
        )
    }

    /**
     * 获取文件后缀名
     */
    private fun getFileSuffix(fileName: String): String {
        val lastDotIndex = fileName.lastIndexOf('.')
        return if (lastDotIndex > 0) {
            fileName.substring(lastDotIndex)
        } else {
            ""
        }
    }
}
