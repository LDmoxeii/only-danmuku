package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoPostProcessing
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.videoPostId
import edu.only4.danmuku.application.queries.video_post_processing.UniqueVideoPostProcessingQry
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
class UniqueVideoPostProcessingQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoPostProcessingQry.Request, UniqueVideoPostProcessingQry.Response> {

    override fun exec(request: UniqueVideoPostProcessingQry.Request): UniqueVideoPostProcessingQry.Response {
        val exists = sqlClient.exists(VideoPostProcessing::class) {
            where(table.videoPostId eq request.videoPostId)
            where(table.id `ne?` request.excludeVideoPostProcessingId)
        }

        return UniqueVideoPostProcessingQry.Response(
            exists = exists
        )
    }
}
