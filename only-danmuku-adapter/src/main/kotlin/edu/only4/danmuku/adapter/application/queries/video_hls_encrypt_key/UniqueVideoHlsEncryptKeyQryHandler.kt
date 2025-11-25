package edu.only4.danmuku.adapter.application.queries.video_hls_encrypt_key

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.VideoHlsEncryptKey
import edu.only4.danmuku.application.queries._share.model.fileId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.keyId
import edu.only4.danmuku.application.queries._share.model.keyVersion
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries.video_hls_encrypt_key.UniqueVideoHlsEncryptKeyQry
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
class UniqueVideoHlsEncryptKeyQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoHlsEncryptKeyQry.Request, UniqueVideoHlsEncryptKeyQry.Response> {

    override fun exec(request: UniqueVideoHlsEncryptKeyQry.Request): UniqueVideoHlsEncryptKeyQry.Response {
        val exists = sqlClient.exists(VideoHlsEncryptKey::class) {
            where(table.fileId eq request.fileId)
            where(table.keyId eq request.keyId)
            where(table.keyVersion eq request.keyVersion)
            where(table.quality eq request.quality)
            where(table.id `ne?` request.excludeVideoHlsEncryptKeyId)
        }

        return UniqueVideoHlsEncryptKeyQry.Response(
            exists = exists
        )
    }
}

