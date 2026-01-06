package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.transcodeOutputPrefix
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 根据稿件态 filePostId 获取存储路径
 */
@Service
class GetVideoFilePostPathQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoFilePostPathQry.Request, GetVideoFilePostPathQry.Response> {
    override fun exec(request: GetVideoFilePostPathQry.Request): GetVideoFilePostPathQry.Response {
        val outputPrefix = sqlClient.createQuery(VideoFilePost::class) {
            where(table.id eq request.filePostId)
            select(table.transcodeOutputPrefix)
        }.fetchOneOrNull() ?: throw KnownException("稿件态文件不存在: ${request.filePostId}")
        val path = outputPrefix.takeIf { it.isNotBlank() }
            ?: throw KnownException("播放路径为空: ${request.filePostId}")
        return GetVideoFilePostPathQry.Response(filePath = path)
    }
}
