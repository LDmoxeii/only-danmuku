package edu.only4.danmuku.adapter.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.video_comment.JVideoComment
import edu.only4.danmuku.application.queries._share.model.video_comment.id
import edu.only4.danmuku.application.queries.video_comment.CommentExistsByIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

@Service
class CommentExistsByIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CommentExistsByIdQry.Request, CommentExistsByIdQry.Response> {

    override fun exec(request: CommentExistsByIdQry.Request): CommentExistsByIdQry.Response {
        val exists = sqlClient.exists(JVideoComment::class) {
            where(table.id eq request.commentId)
        }
        return CommentExistsByIdQry.Response(exists)
    }
}
