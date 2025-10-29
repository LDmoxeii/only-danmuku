package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.Video
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.videoPostId

import edu.only4.danmuku.application.queries.video.VideoExistByVideoPostIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq

import org.springframework.stereotype.Service

/**
 * 通过视频稿件Id判断视频是否存在
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/29
 */
@Service
class VideoExistByVideoPostIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<VideoExistByVideoPostIdQry.Request, VideoExistByVideoPostIdQry.Response> {

    override fun exec(request: VideoExistByVideoPostIdQry.Request): VideoExistByVideoPostIdQry.Response {
        val videoId = sqlClient.createQuery(Video::class) {
            where(table.videoPostId eq request.videoPostId)
            select(table.id)
        }.fetchOneOrNull()

        return VideoExistByVideoPostIdQry.Response(
            exist = videoId != null,
            videoId = videoId
        )
    }
}
