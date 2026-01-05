package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyVersionQry
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询稿件最新 keyVersion（按批次）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class GetLatestVideoHlsKeyVersionQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetLatestVideoHlsKeyVersionQry.Request, GetLatestVideoHlsKeyVersionQry.Response> {

    override fun exec(request: GetLatestVideoHlsKeyVersionQry.Request): GetLatestVideoHlsKeyVersionQry.Response {
        val versions = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.videoPostId eq request.videoPostId)
            where(table.fileIndex eq request.fileIndex)
            where(table.status eq EncryptKeyStatus.ACTIVE)
            select(table.keyVersion)
        }.execute()
        val latestVersion = versions.maxOrNull() ?: return GetLatestVideoHlsKeyVersionQry.Response(
            keyVersion = null,
            qualities = null
        )

        val qualities = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.videoPostId eq request.videoPostId)
            where(table.fileIndex eq request.fileIndex)
            where(table.keyVersion eq latestVersion)
            where(table.status eq EncryptKeyStatus.ACTIVE)
            select(table.quality)
        }.execute()
            .filterNotNull()
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()
            .sortedWith(compareByDescending<String> { qualityScore(it) }.thenBy { it })

        return GetLatestVideoHlsKeyVersionQry.Response(
            keyVersion = latestVersion,
            qualities = qualities
        )
    }

    private fun qualityScore(quality: String): Int {
        val number = QUALITY_NUMBER_REGEX.find(quality)?.groupValues?.getOrNull(1)?.toIntOrNull()
        return number ?: Int.MIN_VALUE
    }

    companion object {
        private val QUALITY_NUMBER_REGEX = Regex("(\\d+)")
    }
}
