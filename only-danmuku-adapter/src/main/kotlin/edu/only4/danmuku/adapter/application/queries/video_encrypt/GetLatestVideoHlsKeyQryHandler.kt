package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyQry
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询可用的最新密钥元数据（不返回明文），用于拼接 m3u8 key URI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class GetLatestVideoHlsKeyQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetLatestVideoHlsKeyQry.Request, GetLatestVideoHlsKeyQry.Response> {

    override fun exec(request: GetLatestVideoHlsKeyQry.Request): GetLatestVideoHlsKeyQry.Response {
        val filePostId = resolveFilePostId(request) ?: return GetLatestVideoHlsKeyQry.Response(
            keyId = null,
            keyVersion = null,
            keyQuality = null,
            keyUriTemplate = null,
            status = "NOT_FOUND"
        )

        val key = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.fileId eq filePostId)
            where(table.status eq EncryptKeyStatus.ACTIVE)
            orderBy(table.keyVersion.desc())
            select(table)
        }.fetchOneOrNull()

        return GetLatestVideoHlsKeyQry.Response(
            keyId = key?.keyId,
            keyVersion = key?.keyVersion,
            keyQuality = key?.quality,
            keyUriTemplate = key?.keyUriTemplate,
            status = key?.status?.name ?: "NOT_FOUND"
        )
    }

    private fun resolveFilePostId(request: GetLatestVideoHlsKeyQry.Request): Long? {
        request.videoFilePostId?.let { return it }
        val videoFileId = request.videoFileId ?: return null
        return sqlClient.createQuery(VideoFile::class) {
            where(table.id eq videoFileId)
            select(table.videoFilePostId)
        }.fetchOneOrNull()
    }
}
