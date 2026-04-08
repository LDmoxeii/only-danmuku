package edu.only4.danmuku.adapter.application.queries.video

import com.only.engine.error.CommonErrors
import com.only.engine.exception.AppException
import com.only.engine.exception.BusinessException
import com.only.engine.exception.DependencyException
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import edu.only4.danmuku.domain.shared.error.DanmukuBusinessErrors
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.video.GetVideoFileContextByIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

@Service
class GetVideoFileContextByIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoFileContextByIdQry.Request, GetVideoFileContextByIdQry.Response> {

    override fun exec(request: GetVideoFileContextByIdQry.Request): GetVideoFileContextByIdQry.Response {
        val row = sqlClient.createQuery(VideoFile::class) {
            where(table.id eq request.fileId)
            select(
                table.fetchBy {
                    fileIndex()
                    video {
                        videoPost()
                    }
                }
            )
        }.fetchOneOrNull() ?: throw BusinessException(DanmukuBusinessErrors.RESOURCE_NOT_FOUND, "文件不存在: ${request.fileId}")

        return GetVideoFileContextByIdQry.Response(
            videoId = row.video.id,
            videoPostId = row.video.videoPost.id,
            fileIndex = row.fileIndex
        )
    }
}
