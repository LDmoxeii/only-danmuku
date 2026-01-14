package edu.only4.danmuku.adapter.application.queries.video_quality_policy

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.VideoQualityPolicy
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries._share.model.videoId
import edu.only4.danmuku.application.queries.video_quality_policy.UniqueVideoQualityPolicyQry
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
class UniqueVideoQualityPolicyQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoQualityPolicyQry.Request, UniqueVideoQualityPolicyQry.Response> {

    override fun exec(request: UniqueVideoQualityPolicyQry.Request): UniqueVideoQualityPolicyQry.Response {
        val exists = sqlClient.exists(VideoQualityPolicy::class) {
            where(table.videoId eq request.videoId)
            where(table.fileIndex eq request.fileIndex)
            where(table.quality eq request.quality)
            where(table.id `ne?` request.excludeVideoQualityPolicyId)
        }

        return UniqueVideoQualityPolicyQry.Response(
            exists = exists
        )
    }
}

