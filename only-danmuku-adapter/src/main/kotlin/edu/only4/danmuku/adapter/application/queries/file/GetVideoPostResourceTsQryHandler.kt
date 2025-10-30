package edu.only4.danmuku.adapter.application.queries.file

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.file.GetVideoPostResourceTsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import java.io.File

/**
 * 获取视频TS分片资源查询处理器
 */
@Service
class GetVideoPostResourceTsQryHandler(
    private val fileProps: FileAppProperties,
    private val sqlClient: KSqlClient,
) : Query<GetVideoPostResourceTsQry.Request, GetVideoPostResourceTsQry.Response> {

    override fun exec(request: GetVideoPostResourceTsQry.Request): GetVideoPostResourceTsQry.Response {
        // 根据文件ID查询视频文件信息
        val videoFile = sqlClient.createQuery(VideoFilePost::class) {
            where(table.id eq request.fileId)
            select(table)
        }.fetchOneOrNull()

        if (videoFile == null) {
            return GetVideoPostResourceTsQry.Response(
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

        return GetVideoPostResourceTsQry.Response(
            filePath = fullPath,
            exists = file.exists() && file.isFile
        )
    }
}
