package edu.only4.danmuku.adapter.application.queries.video_hls_quality_auth

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.VideoHlsQualityAuth
import edu.only4.danmuku.application.queries._share.model.fileId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries.video_hls_quality_auth.UniqueVideoHlsQualityAuthQry
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
class UniqueVideoHlsQualityAuthQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoHlsQualityAuthQry.Request, UniqueVideoHlsQualityAuthQry.Response> {

    override fun exec(request: UniqueVideoHlsQualityAuthQry.Request): UniqueVideoHlsQualityAuthQry.Response {
        val exists = sqlClient.exists(VideoHlsQualityAuth::class) {
            where(table.fileId eq request.fileId)
            where(table.quality eq request.quality)
            where(table.id `ne?` request.excludeVideoHlsQualityAuthId)
        }

        return UniqueVideoHlsQualityAuthQry.Response(
            exists = exists
        )
    }
}

