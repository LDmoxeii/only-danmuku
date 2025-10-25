package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.draft.video_file.VideoFileItem
import edu.only4.danmuku.application.queries._share.model.video_file.JVideoFile
import edu.only4.danmuku.application.queries._share.model.video_file.fileIndex
import edu.only4.danmuku.application.queries._share.model.video_file.videoId
import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取视频播放文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoPlayFilesQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetVideoPlayFilesQry.Request, GetVideoPlayFilesQry.Response> {

    override fun exec(request: GetVideoPlayFilesQry.Request): List<GetVideoPlayFilesQry.Response> {
        // 使用 Jimmer 查询视频文件列表
        val fileList = sqlClient.createQuery(JVideoFile::class) {
            // 按视频ID过滤
            where(table.videoId eq request.videoId)
            // 按文件索引排序
            orderBy(table.fileIndex.asc())
            // DTO 投影
            select(table.fetch(VideoFileItem::class))
        }.execute()

        // 转换为查询响应
        return fileList.map { file ->
            GetVideoPlayFilesQry.Response(
                fileId = file.id,
                videoId = file.videoId,
                fileIndex = file.fileIndex,
                fileName = file.fileName,
                fileSize = file.fileSize,
                filePath = file.filePath,
                duration = file.duration
            )
        }
    }
}
