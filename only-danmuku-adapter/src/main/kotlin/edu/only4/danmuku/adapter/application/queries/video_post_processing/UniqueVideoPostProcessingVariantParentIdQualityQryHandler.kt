package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.VideoPostProcessingVariant
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.parentId
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries._share.model.videoPostProcessingFileId
import edu.only4.danmuku.application.queries._share.model.videoPostProcessingId
import edu.only4.danmuku.application.queries.video_post_processing.UniqueVideoPostProcessingVariantParentIdQualityQry
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
class UniqueVideoPostProcessingVariantParentIdQualityQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoPostProcessingVariantParentIdQualityQry.Request, UniqueVideoPostProcessingVariantParentIdQualityQry.Response> {

    override fun exec(request: UniqueVideoPostProcessingVariantParentIdQualityQry.Request): UniqueVideoPostProcessingVariantParentIdQualityQry.Response {
        val exists = sqlClient.exists(VideoPostProcessingVariant::class) {
            where(table.videoPostProcessingFileId eq request.parentId)
            where(table.quality eq request.quality)
            where(table.id `ne?` request.excludeVideoPostProcessingVariantId)
        }

        return UniqueVideoPostProcessingVariantParentIdQualityQry.Response(
            exists = exists
        )
    }
}

