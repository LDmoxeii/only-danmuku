package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.VideoHlsEncryptKey
import edu.only4.danmuku.application.queries._share.model.fileId
import edu.only4.danmuku.application.queries._share.model.keyVersion
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries._share.model.status
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsEncryptKeysQry
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 按 fileId + keyVersion 查询质量 key 列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class ListVideoHlsEncryptKeysQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoHlsEncryptKeysQry.Request, ListVideoHlsEncryptKeysQry.Response> {

    override fun exec(request: ListVideoHlsEncryptKeysQry.Request): List<ListVideoHlsEncryptKeysQry.Response> {
        val filePostId = request.videoFilePostId
        val keyVersion = request.keyVersion ?: resolveLatestKeyVersion(filePostId) ?: return emptyList()

        val keys = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.fileId eq filePostId)
            where(table.keyVersion eq keyVersion)
            select(table)
        }.execute()
        if (keys.isEmpty()) return emptyList()

        val sorted = keys.sortedWith(
            compareByDescending<VideoHlsEncryptKey> { qualityScore(it.quality ?: "") }
                .thenBy { it.quality ?: "" }
        )
        val payloads = sorted.map {
            KeyItem(
                quality = it.quality,
                keyId = it.keyId,
                keyVersion = it.keyVersion,
                status = it.status.name,
                keyUriTemplate = it.keyUriTemplate
            )
        }
        val keysJson = JsonUtils.toJsonString(payloads) ?: "[]"
        return listOf(ListVideoHlsEncryptKeysQry.Response(keysJson = keysJson))

    }

    private fun resolveLatestKeyVersion(filePostId: Long): Int? {
        val versions = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.fileId eq filePostId)
            where(table.status eq EncryptKeyStatus.ACTIVE)
            select(table.keyVersion)
        }.execute()
        return versions.maxOrNull()
    }

    private fun qualityScore(quality: String): Int {
        val number = QUALITY_NUMBER_REGEX.find(quality)?.groupValues?.getOrNull(1)?.toIntOrNull()
        return number ?: Int.MIN_VALUE
    }

    data class KeyItem(
        val quality: String?,
        val keyId: String,
        val keyVersion: Int,
        val status: String,
        val keyUriTemplate: String
    )

    companion object {
        private val QUALITY_NUMBER_REGEX = Regex("(\\d+)")
    }
}
