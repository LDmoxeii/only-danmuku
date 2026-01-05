package edu.only4.danmuku.adapter.application.queries.video_file_post

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_file_post.GetVideoFilePostsByPostIdQry
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.duration
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.transferResult
import edu.only4.danmuku.application.queries._share.model.uploadId
import edu.only4.danmuku.application.queries._share.model.videoPost

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq

/**
 * 查询稿件下的所有 VideoFilePost（含转码状态）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class GetVideoFilePostsByPostIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoFilePostsByPostIdQry.Request, GetVideoFilePostsByPostIdQry.Response> {

    override fun exec(request: GetVideoFilePostsByPostIdQry.Request): GetVideoFilePostsByPostIdQry.Response {

        val list = sqlClient.createQuery(VideoFilePost::class) {
            where(table.videoPost.id eq request.videoPostId)
            orderBy(table.fileIndex.asc())
            select(
                table.id,
                table.uploadId,
                table.fileIndex,
                table.transferResult,
                table.duration
            )
        }.execute().map {
            val (id, uploadId, fileIndex, transferResult, duration) = it
            GetVideoFilePostsByPostIdQry.FileItem(
                videoFilePostId = id,
                uploadId = uploadId,
                fileIndex = fileIndex,
                transferResult = transferResult.code,
                duration = duration
            )
        }

        return GetVideoFilePostsByPostIdQry.Response(files = list)
    }
}
