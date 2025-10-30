package edu.only4.danmuku.adapter.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoComment
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.video_comment.GetCommentByIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 根据ID获取评论
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCommentByIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetCommentByIdQry.Request, GetCommentByIdQry.Response> {

    override fun exec(request: GetCommentByIdQry.Request): GetCommentByIdQry.Response {
        val item = sqlClient.createQuery(VideoComment::class) {
            where(table.id eq request.commentId)
            select(table)
        }.execute().firstOrNull() ?: throw RuntimeException("评论不存在: ${request.commentId}")

        return GetCommentByIdQry.Response(
            commentId = item.id,
            videoId = item.video.id,
            videoOwnerId = item.videoOwner.id,
            userId = item.customer.id,
            parentId = item.parentId ?: 0L,
            content = item.content,
        )
    }
}
