package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.filePath
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.video
import edu.only4.danmuku.application.queries._share.model.videoFilePostId
import edu.only4.danmuku.application.queries._share.model.videoPost
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

        val postId = row._1 ?: throw KnownException("缺少稿件态 fileId: ${request.fileId}")
        val postRow = sqlClient.createQuery(VideoFilePost::class) {
            where(table.id eq postId)
            select(table.videoPost.id, table.fileIndex)
        }.fetchOneOrNull() ?: throw KnownException("稿件文件不存在: $postId")
        return GetVideoPostIdByFileIdQry.Response(
            filePostId = postId,
            filePath = row._2,
            videoPostId = postRow._1,
            fileIndex = postRow._2,
        )
    }
}
