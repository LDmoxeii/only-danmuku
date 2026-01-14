package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.VideoPostProcessingVariant
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.parentId
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries.video_post_processing.UniqueVideoPostProcessingVariantQry
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
class UniqueVideoPostProcessingVariantQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoPostProcessingVariantQry.Request, UniqueVideoPostProcessingVariantQry.Response> {

    override fun exec(request: UniqueVideoPostProcessingVariantQry.Request): UniqueVideoPostProcessingVariantQry.Response {
        val exists = sqlClient.exists(VideoPostProcessingVariant::class) {
            where(table.parentId eq request.parentId)
            where(table.quality eq request.quality)
            where(table.id `ne?` request.excludeVideoPostProcessingVariantId)
        }

        return UniqueVideoPostProcessingVariantQry.Response(
            exists = exists
        )
    }
}

