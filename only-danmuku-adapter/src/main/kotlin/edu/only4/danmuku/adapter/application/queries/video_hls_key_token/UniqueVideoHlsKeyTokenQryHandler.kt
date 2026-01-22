package edu.only4.danmuku.adapter.application.queries.video_hls_key_token

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoHlsKeyToken
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.tokenHash
import edu.only4.danmuku.application.queries.video_hls_key_token.UniqueVideoHlsKeyTokenQry
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
class UniqueVideoHlsKeyTokenQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoHlsKeyTokenQry.Request, UniqueVideoHlsKeyTokenQry.Response> {

    override fun exec(request: UniqueVideoHlsKeyTokenQry.Request): UniqueVideoHlsKeyTokenQry.Response {
        val exists = sqlClient.exists(VideoHlsKeyToken::class) {
            where(table.tokenHash eq request.tokenHash)
            where(table.id `ne?` request.excludeVideoHlsKeyTokenId)
        }

        return UniqueVideoHlsKeyTokenQry.Response(
            exists = exists
        )
    }
}
