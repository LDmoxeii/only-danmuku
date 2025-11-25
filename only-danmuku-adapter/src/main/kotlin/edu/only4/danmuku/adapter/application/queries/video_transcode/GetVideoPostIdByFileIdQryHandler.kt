package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.filePath
import edu.only4.danmuku.application.queries._share.model.videoFilePostId
import edu.only4.danmuku.application.queries.video_transcode.GetVideoPostIdByFileIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 前台 fileId 映射到稿件态 filePostId 及路径
 */
@Service
class GetVideoPostIdByFileIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoPostIdByFileIdQry.Request, GetVideoPostIdByFileIdQry.Response> {
    override fun exec(request: GetVideoPostIdByFileIdQry.Request): GetVideoPostIdByFileIdQry.Response {
        val row = sqlClient.createQuery(VideoFile::class) {
            where(table.id eq request.fileId)
            select(table.videoFilePostId, table.filePath)
        }.fetchOneOrNull() ?: throw KnownException("文件不存在: ${request.fileId}")

        val postId = row.first ?: throw KnownException("缺少稿件态 fileId: ${request.fileId}")
        return GetVideoPostIdByFileIdQry.Response(
            filePostId = postId,
            filePath = row.second
        )
    }
}
