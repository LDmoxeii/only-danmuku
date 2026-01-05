package edu.only4.danmuku.adapter.application.queries.video_post

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFilePostVariant
import edu.only4.danmuku.application.queries._share.model.filePostId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries.video_post.UniqueVideoFilePostVariantParentIdQualityQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Service
class UniqueVideoFilePostVariantParentIdQualityQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoFilePostVariantParentIdQualityQry.Request, UniqueVideoFilePostVariantParentIdQualityQry.Response> {

    override fun exec(request: UniqueVideoFilePostVariantParentIdQualityQry.Request): UniqueVideoFilePostVariantParentIdQualityQry.Response {
        val exists = sqlClient.exists(VideoFilePostVariant::class) {
            where(table.filePostId eq request.parentId)
            where(table.quality eq request.quality)
            where(table.id `ne?` request.excludeVideoFilePostVariantId)
        }

        return UniqueVideoFilePostVariantParentIdQualityQry.Response(
            exists = exists
        )
    }
}

