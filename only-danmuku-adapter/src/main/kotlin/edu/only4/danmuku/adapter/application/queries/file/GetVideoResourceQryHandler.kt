package edu.only4.danmuku.adapter.application.queries.file

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries.file.GetVideoResourceQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import java.io.File

/**
 * 获取视频资源查询处理器（m3u8）
 */
@Service
class GetVideoResourceQryHandler(
    private val fileProps: FileAppProperties,
    private val sqlClient: KSqlClient,
) : Query<GetVideoResourceQry.Request, GetVideoResourceQry.Response> {

    override fun exec(request: GetVideoResourceQry.Request): GetVideoResourceQry.Response {
        // 根据文件ID查询视频文件信息
        val videoFile = sqlClient.createQuery(VideoFile::class) {
            where(table.id eq request.fileId.toLongOrNull())
            select(table.fetch(VideoFile::filePath, VideoFile::fileIndex, VideoFile::video))
        }.fetchOneOrNull()

        if (videoFile == null) {
            return GetVideoResourceQry.Response(
                filePath = "",
                exists = false,
                videoId = null,
                fileIndex = null
            )
        }

        // 构造m3u8文件完整路径
        val fullPath = fileProps.projectFolder +
            Constants.FILE_FOLDER +
            videoFile.filePath + "/" +
            Constants.M3U8_NAME

        val file = File(fullPath)

        return GetVideoResourceQry.Response(
            filePath = fullPath,
            exists = file.exists() && file.isFile,
            videoId = videoFile.video.id,
            fileIndex = videoFile.fileIndex
        )
    }
}
