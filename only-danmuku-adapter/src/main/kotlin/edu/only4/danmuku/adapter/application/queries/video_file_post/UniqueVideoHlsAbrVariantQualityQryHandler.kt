package edu.only4.danmuku.adapter.application.queries.video_file_post

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.VideoHlsAbrVariant
import edu.only4.danmuku.application.queries._share.model.fileId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries.video_file_post.UniqueVideoHlsAbrVariantQualityQry
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
class UniqueVideoHlsAbrVariantQualityQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoHlsAbrVariantQualityQry.Request, UniqueVideoHlsAbrVariantQualityQry.Response> {

    override fun exec(request: UniqueVideoHlsAbrVariantQualityQry.Request): UniqueVideoHlsAbrVariantQualityQry.Response {
        val exists = sqlClient.exists(VideoHlsAbrVariant::class) {
            where(table.fileId eq request.fileId)
            where(table.quality eq request.quality)
            where(table.id `ne?` request.excludeVideoHlsAbrVariantId)
        }

        return UniqueVideoHlsAbrVariantQualityQry.Response(
            exists = exists
        )
    }
}

