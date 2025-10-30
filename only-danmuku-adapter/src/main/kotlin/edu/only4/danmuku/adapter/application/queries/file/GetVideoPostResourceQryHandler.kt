package edu.only4.danmuku.adapter.application.queries.file

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.file.GetVideoPostResourceQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import java.io.File

/**
 * 获取视频资源查询处理器（m3u8）
 */
@Service
class GetVideoPostResourceQryHandler(
    private val fileProps: FileAppProperties,
    private val sqlClient: KSqlClient,
) : Query<GetVideoPostResourceQry.Request, GetVideoPostResourceQry.Response> {

    override fun exec(request: GetVideoPostResourceQry.Request): GetVideoPostResourceQry.Response {
        // 根据文件ID查询视频文件信息
        val videoFile = sqlClient.createQuery(VideoFilePost::class) {
            where(table.id eq request.fileId)
            select(table)
        }.fetchOne()

        // 构造m3u8文件完整路径
        val fullPath = fileProps.projectFolder +
            Constants.FILE_FOLDER +
            videoFile.filePath + "/" +
            Constants.M3U8_NAME

        val file = File(fullPath)

        return GetVideoPostResourceQry.Response(
            filePath = fullPath,
            exists = file.exists() && file.isFile,
            videoId = videoFile.video.id,
            fileIndex = videoFile.fileIndex
        )
    }
}
