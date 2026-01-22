package edu.only4.danmuku.adapter.application.queries.video_post

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.videoPostId
import edu.only4.danmuku.application.queries.video_post.UniqueVideoFilePostQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Service
class UniqueVideoFilePostQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoFilePostQry.Request, UniqueVideoFilePostQry.Response> {

    override fun exec(request: UniqueVideoFilePostQry.Request): UniqueVideoFilePostQry.Response {
        val exists = sqlClient.exists(VideoFilePost::class) {
            where(table.videoPostId eq request.videoPostId)
            where(table.fileIndex eq request.fileIndex)
            where(table.id `ne?` request.excludeVideoFilePostId)
        }

        return UniqueVideoFilePostQry.Response(
            exists = exists
        )
    }
}
