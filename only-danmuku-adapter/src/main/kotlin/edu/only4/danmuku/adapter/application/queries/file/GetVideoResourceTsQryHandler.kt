package edu.only4.danmuku.adapter.application.queries.file

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.category.id
import edu.only4.danmuku.application.queries.file.GetVideoResourceTsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import java.io.File

/**
 * 获取视频TS分片资源查询处理器
 */
@Service
class GetVideoResourceTsQryHandler(
    private val fileProps: FileAppProperties,
    private val sqlClient: KSqlClient,
) : Query<GetVideoResourceTsQry.Request, GetVideoResourceTsQry.Response> {

    override fun exec(request: GetVideoResourceTsQry.Request): GetVideoResourceTsQry.Response {
        // 根据文件ID查询视频文件信息
        val videoFile = sqlClient.createQuery(VideoFile::class) {
            where(table.id eq request.fileId.toLongOrNull())
            select(table)
        }.fetchOneOrNull()

        if (videoFile == null) {
            return GetVideoResourceTsQry.Response(
                filePath = "",
                exists = false
            )
        }

        // 构造TS文件完整路径
        val fullPath = fileProps.projectFolder +
            Constants.FILE_FOLDER +
            videoFile.filePath + "/" +
            request.ts

        val file = File(fullPath)

        return GetVideoResourceTsQry.Response(
            filePath = fullPath,
            exists = file.exists() && file.isFile
        )
    }
}
