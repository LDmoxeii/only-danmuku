package edu.only4.danmuku.adapter.application.queries.file

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
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

    override fun exec(request: GetFileResourceQry.Request): GetFileResourceQry.Response {
        val sourceName = request.sourceName

        val fileSuffix = getFileSuffix(sourceName)

        val fullPath = fileProps.projectFolder + Constants.FILE_FOLDER + sourceName
        val file = File(fullPath)

        return GetFileResourceQry.Response(
            filePath = fullPath,
            fileSuffix = fileSuffix,
            exists = file.exists() && file.isFile
        )
    }

    private fun getFileSuffix(fileName: String): String {
        val lastDotIndex = fileName.lastIndexOf('.')
        return if (lastDotIndex > 0) {
            fileName.substring(lastDotIndex)
        } else {
            ""
        }
    }
}
